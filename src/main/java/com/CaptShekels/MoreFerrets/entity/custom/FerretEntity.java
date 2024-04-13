package com.CaptShekels.MoreFerrets.entity.custom;

import com.CaptShekels.MoreFerrets.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FerretEntity extends Animal implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
    private static final List<Class<? extends Animal>> TARGET_BREEDING_CLASSES = Arrays.asList(FerretStandardEntity.class, FerretPandaEntity.class);

    // Declare cached animations
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().then("animation.ferret.walk", Animation.LoopType.LOOP);
    private static final RawAnimation WALK_ANIM_BEG = RawAnimation.begin().then("animation.ferret.walk.beg", Animation.LoopType.LOOP);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().then("animation.ferret.idle", Animation.LoopType.LOOP);
    private static final RawAnimation BEG_ANIM = RawAnimation.begin().then("animation.ferret.idle.beg", Animation.LoopType.PLAY_ONCE);

    private int salt;
    private FerretType TYPE;
    private TemptGoal temptGoal;
    private AnimationController<?> triggerableController;
    public static final Ingredient FOOD_ITEMS = Ingredient.of(Items.CHICKEN, Items.COD, Items.SALMON);

    public FerretEntity(EntityType<? extends FerretEntity> entityType, Level level) {
        super(entityType, level);
        this.salt = 50 + (int)(Math.random() * 50);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        this.triggerableController =new AnimationController<>(this, "triggerableController", state -> PlayState.STOP)
                .triggerableAnim("beg", BEG_ANIM);

        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllers.add(this.triggerableController);
    }

    private PlayState predicate(AnimationState<FerretEntity> ferretEntityAnimationState) {
        if (ferretEntityAnimationState.isMoving() && checkTriggerableAnimationState(BEG_ANIM)) {
            ferretEntityAnimationState.getController().setAnimation(WALK_ANIM_BEG);
            return PlayState.CONTINUE;
        } else if (ferretEntityAnimationState.isMoving()) {
            ferretEntityAnimationState.getController().setAnimation(WALK_ANIM);
            return PlayState.CONTINUE;
        }

        ferretEntityAnimationState.getController().setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    private boolean checkTriggerableAnimationState(RawAnimation target) {
        return this.triggerableController.getCurrentRawAnimation() != null && this.triggerableController.getCurrentRawAnimation().equals(target);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f).build();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.temptGoal = new TemptGoal(this, 1.2D, FOOD_ITEMS, false);
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // Mob Breeding Goals
        this.goalSelector.addGoal(1, new FerretBreedGoal(this, 1.15D, TARGET_BREEDING_CLASSES));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.1D));
        this.goalSelector.addGoal(3, this.temptGoal);
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));

        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Chicken.class, true, ((entity) -> !entity.isBaby())));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Rabbit.class, true, ((entity) -> !entity.isBaby())));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return switch (this.random.nextInt(3)) {
            case 1, 2 -> ModSounds.FERRET_IDLE_ONE.get();
            default -> ModSounds.FERRET_IDLE_TWO.get();
        };
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return ModSounds.FERRET_HIT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.FERRET_DEATH.get();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.temptGoal != null && this.temptGoal.isRunning() && this.tickCount % this.salt == 0) {
            triggerAnim("triggerableController", "beg");
        }
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack);
    }
    public void setFerretType(FerretType type) { this.TYPE = type; }
    public FerretType getFerretType() { return this.TYPE; }

    public enum FerretType {
        STANDARD,
        PANDA;
    }

    public static class FerretBreedGoal extends BreedGoal {
        List<Class<? extends Animal>> targetClassList;
        public FerretBreedGoal(Animal p_25122_, double p_25123_) {
            super(p_25122_, p_25123_);
        }
        public FerretBreedGoal(Animal p_25122_, double p_25123_, List<Class<? extends Animal>> targetClassList) {
            super(p_25122_, p_25123_);
            this.targetClassList = targetClassList;
        }

        public boolean canUse() {
            if (!this.animal.isInLove()) {
                return false;
            } else {
                this.partner = this.getFreePartner();
                return this.partner != null;
            }
        }

        @Nullable
        private Animal getFreePartner() {
            List<Animal> list = new ArrayList<>();
            for(Class<? extends Animal> animalClass : targetClassList) {
                list.addAll(this.level.getNearbyEntities(animalClass, PARTNER_TARGETING, this.animal, this.animal.getBoundingBox().inflate(8.0D)));
            }
            double d0 = Double.MAX_VALUE;
            Animal animal = null;

            for(Animal animal1 : list) {
                if ((this.animal.canMate(animal1) || TARGET_BREEDING_CLASSES.contains(animal1.getClass())) && this.animal.distanceToSqr(animal1) < d0) {
                    animal = animal1;
                    d0 = this.animal.distanceToSqr(animal1);
                }
            }

            return animal;
        }

    }
}
