/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.worldgrower.Constants;
import org.worldgrower.DoNothingOnTurn;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.WorldObjectImpl;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.Demands;
import org.worldgrower.attribute.GhostImageIds;
import org.worldgrower.attribute.IdList;
import org.worldgrower.attribute.LookDirection;
import org.worldgrower.attribute.ManagedProperty;
import org.worldgrower.attribute.SkillUtils;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.condition.Conditions;
import org.worldgrower.creaturetype.CreatureType;
import org.worldgrower.gui.ImageIds;

public class CreatureGenerator implements Serializable {
	private final WorldObject organization;
	private final Random random = new Random(0);
	
	public CreatureGenerator(WorldObject organization) {
		this.organization = organization;
	}
	
	public List<WorldObject> getCreatures(int width, int height) {
		List<WorldObject> creatures = new ArrayList<>();
		creatures.add(generateRat(0, 0, 0));
		creatures.add(generateSpider(0, 0, 0));
		creatures.add(generateSlime(0, 0, 0));
		creatures.add(generateMinotaur(0, 0, 0));
		
		creatures = creatures.stream().filter(w -> w.getProperty(Constants.WIDTH) == width && w.getProperty(Constants.HEIGHT) == height).collect(Collectors.toList());
		
		return creatures;
	}

	public int generateRat(int x, int y, World world) {
		
		int id = world.generateUniqueId();
		WorldObject rat = generateRat(x, y, id);
		world.addWorldObject(rat);
		
		return id;
	}

	private WorldObject generateRat(int x, int y, int id) {
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		properties.put(Constants.X, x);
		properties.put(Constants.Y, y);
		properties.put(Constants.WIDTH, 1);
		properties.put(Constants.HEIGHT, 1);
		properties.put(Constants.HIT_POINTS, 2 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.HIT_POINTS_MAX, 3 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.NAME, "Rat");
		properties.put(Constants.LEVEL, 1);
		properties.put(Constants.ID, id);
		properties.put(Constants.IMAGE_ID, ImageIds.RAT);
		properties.put(Constants.FOOD, 200);
		properties.put(Constants.WATER, 200);
		properties.put(Constants.ENERGY, 1000);
		properties.put(Constants.GROUP, new IdList().add(organization));
		properties.put(Constants.GOLD, 0);
		properties.put(Constants.DEMANDS, new Demands());
		properties.put(Constants.CHILDREN, new IdList());
		properties.put(Constants.SOCIAL, 500);
		properties.put(Constants.GENDER, generateGender());
		properties.put(Constants.CREATURE_TYPE, CreatureType.RAT_CREATURE_TYPE);
		properties.put(Constants.CONDITIONS, new Conditions());
		
		properties.put(Constants.ARMOR, 10);
		
		properties.put(Constants.STRENGTH, 6);
		properties.put(Constants.DEXTERITY, 12);
		properties.put(Constants.CONSTITUTION, 8);
		properties.put(Constants.INTELLIGENCE, 6);
		properties.put(Constants.WISDOM, 12);
		properties.put(Constants.CHARISMA, 6);
		
		SkillUtils.addAllSkills(properties);
		
		properties.put(Constants.DAMAGE, 2 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.DAMAGE_RESIST, 0);
		
		return new WorldObjectImpl(properties, Actions.ALL_ACTIONS, new BeastOnTurn(this::generateRat), new RatWorldEvaluationFunction());
	}

	private String generateGender() {
		final String gender;
		if (random.nextFloat() > 0.5f) {
			gender = "female";
		} else {
			gender = "male";
		}
		return gender;
	}
	
	public int generateSpider(int x, int y, World world) {
		
		int id = world.generateUniqueId();
		WorldObject spider = generateSpider(x, y, id);
		world.addWorldObject(spider);
		
		return id;
	}

	private WorldObject generateSpider(int x, int y, int id) {
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		properties.put(Constants.X, x);
		properties.put(Constants.Y, y);
		properties.put(Constants.WIDTH, 1);
		properties.put(Constants.HEIGHT, 1);
		properties.put(Constants.HIT_POINTS, 9 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.HIT_POINTS_MAX, 10 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.NAME, "Spider");
		properties.put(Constants.ID, id);
		properties.put(Constants.IMAGE_ID, ImageIds.SPIDER);
		properties.put(Constants.FOOD, 200);
		properties.put(Constants.WATER, 200);
		properties.put(Constants.ENERGY, 1000);
		properties.put(Constants.GROUP, new IdList().add(organization));
		properties.put(Constants.GOLD, 0);
		properties.put(Constants.DEMANDS, new Demands());
		properties.put(Constants.CHILDREN, new IdList());
		properties.put(Constants.SOCIAL, 500);
		properties.put(Constants.GENDER, generateGender());
		properties.put(Constants.CREATURE_TYPE, CreatureType.SPIDER_CREATURE_TYPE);
		properties.put(Constants.CONDITIONS, new Conditions());
		
		properties.put(Constants.ARMOR, 10);
		
		properties.put(Constants.STRENGTH, 6);
		properties.put(Constants.DEXTERITY, 12);
		properties.put(Constants.CONSTITUTION, 8);
		properties.put(Constants.INTELLIGENCE, 6);
		properties.put(Constants.WISDOM, 12);
		properties.put(Constants.CHARISMA, 6);
		
		properties.put(Constants.DAMAGE, 5 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.DAMAGE_RESIST, 10);
		
		WorldObject spider = new WorldObjectImpl(properties, Actions.ALL_ACTIONS, new BeastOnTurn(this::generateSpider), new SpiderWorldEvaluationFunction());
		return spider;
	}
	
	public int generateSlime(int x, int y, World world) {
		
		int id = world.generateUniqueId();
		WorldObject slime = generateSlime(x, y, id);
		world.addWorldObject(slime);
		
		return id;
	}

	private WorldObject generateSlime(int x, int y, int id) {
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		properties.put(Constants.X, x);
		properties.put(Constants.Y, y);
		properties.put(Constants.WIDTH, 1);
		properties.put(Constants.HEIGHT, 1);
		properties.put(Constants.HIT_POINTS, 15 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.HIT_POINTS_MAX, 20 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.NAME, "Slime");
		properties.put(Constants.LEVEL, 1);
		properties.put(Constants.ID, id);
		properties.put(Constants.IMAGE_ID, ImageIds.SLIME);
		properties.put(Constants.FOOD, 500);
		properties.put(Constants.WATER, 500);
		properties.put(Constants.ENERGY, 1000);
		properties.put(Constants.GROUP, new IdList().add(organization));
		properties.put(Constants.GOLD, 0);
		properties.put(Constants.DEMANDS, new Demands());
		properties.put(Constants.CHILDREN, new IdList());
		properties.put(Constants.SOCIAL, 500);
		properties.put(Constants.GENDER, generateGender());
		properties.put(Constants.CREATURE_TYPE, CreatureType.SLIME_CREATURE_TYPE);
		properties.put(Constants.CONDITIONS, new Conditions());
		
		properties.put(Constants.ARMOR, 10);
		
		properties.put(Constants.STRENGTH, 12);
		properties.put(Constants.DEXTERITY, 8);
		properties.put(Constants.CONSTITUTION, 16);
		properties.put(Constants.INTELLIGENCE, 6);
		properties.put(Constants.WISDOM, 12);
		properties.put(Constants.CHARISMA, 6);
		
		SkillUtils.addAllSkills(properties);
		
		properties.put(Constants.DAMAGE, 3 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.DAMAGE_RESIST, 8);
		
		WorldObject slime = new WorldObjectImpl(properties, Actions.ALL_ACTIONS, new BeastOnTurn(this::generateSlime), new SlimeWorldEvaluationFunction());
		return slime;
	}
	
	public static int generateMinotaur(int x, int y, World world) {
		
		int id = world.generateUniqueId();
		WorldObject minotaur = generateMinotaur(x, y, id);
		world.addWorldObject(minotaur);
		
		return id;
	}
	
	private static WorldObject generateMinotaur(int x, int y, int id) {
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		properties.put(Constants.X, x);
		properties.put(Constants.Y, y);
		properties.put(Constants.WIDTH, 1);
		properties.put(Constants.HEIGHT, 1);
		properties.put(Constants.HIT_POINTS, 15 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.HIT_POINTS_MAX, 20 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.NAME, "Minotaur");
		properties.put(Constants.ID, id);
		properties.put(Constants.IMAGE_ID, ImageIds.MINOTAUR);
		properties.put(Constants.FOOD, 500);
		properties.put(Constants.WATER, 500);
		properties.put(Constants.ENERGY, 1000);
		properties.put(Constants.GROUP, new IdList());
		properties.put(Constants.GOLD, 0);
		properties.put(Constants.DEMANDS, new Demands());
		properties.put(Constants.CHILDREN, new IdList());
		properties.put(Constants.SOCIAL, 500);
		properties.put(Constants.GENDER, "male");
		properties.put(Constants.CREATURE_TYPE, CreatureType.MINOTAUR_CREATURE_TYPE);
		properties.put(Constants.CONDITIONS, new Conditions());
		
		properties.put(Constants.ARMOR, 10);
		
		properties.put(Constants.STRENGTH, 12);
		properties.put(Constants.DEXTERITY, 8);
		properties.put(Constants.CONSTITUTION, 16);
		properties.put(Constants.INTELLIGENCE, 6);
		properties.put(Constants.WISDOM, 12);
		properties.put(Constants.CHARISMA, 6);
		
		properties.put(Constants.DAMAGE, 3 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.DAMAGE_RESIST, 8);
		
		return new WorldObjectImpl(properties, Actions.ALL_ACTIONS, new DoNothingOnTurn(), new MinotaurWorldEvaluationFunction());
	}
	
	public static int generateGhost(int x, int y, World world, int remainsId, GhostImageIds ghostImageIds, ImageIds originalImageId, String originalName) {
		
		int id = world.generateUniqueId();
		WorldObject ghost = generateGhost(x, y, id, remainsId, ghostImageIds, originalImageId, originalName);
		world.addWorldObject(ghost);
		
		return id;
	}
	
	private static WorldObject generateGhost(int x, int y, int id, int remainsId, GhostImageIds ghostImageIds, ImageIds originalImageId, String originalName) {
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		properties.put(Constants.X, x);
		properties.put(Constants.Y, y);
		properties.put(Constants.WIDTH, 1);
		properties.put(Constants.HEIGHT, 1);
		properties.put(Constants.HIT_POINTS, 15 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.HIT_POINTS_MAX, 20 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.NAME, originalName + "'s ghost");
		properties.put(Constants.ID, id);
		properties.put(Constants.IMAGE_ID, ghostImageIds.getGhostImageIdFor(originalImageId));
		properties.put(Constants.FOOD, 500);
		properties.put(Constants.WATER, 500);
		properties.put(Constants.ENERGY, 1000);
		properties.put(Constants.GROUP, new IdList());
		properties.put(Constants.GOLD, 0);
		properties.put(Constants.DEMANDS, new Demands());
		properties.put(Constants.CHILDREN, new IdList());
		properties.put(Constants.SOCIAL, 500);
		properties.put(Constants.GENDER, "male");
		properties.put(Constants.CREATURE_TYPE, CreatureType.GHOST_CREATURE_TYPE);
		properties.put(Constants.CONDITIONS, new Conditions());
		properties.put(Constants.REMAINS_ID, remainsId);
		
		properties.put(Constants.ARMOR, 10);
		
		properties.put(Constants.STRENGTH, 12);
		properties.put(Constants.DEXTERITY, 8);
		properties.put(Constants.CONSTITUTION, 16);
		properties.put(Constants.INTELLIGENCE, 6);
		properties.put(Constants.WISDOM, 12);
		properties.put(Constants.CHARISMA, 6);
		
		properties.put(Constants.DAMAGE, 3 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.DAMAGE_RESIST, 8);
		
		return new WorldObjectImpl(properties, Actions.ALL_ACTIONS, new DoNothingOnTurn(), new GhostWorldEvaluationFunction());
	}
	
	public int generateSkeleton(int x, int y, World world, WorldObject performer) {
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		int id = world.generateUniqueId();
		
		properties.put(Constants.X, x);
		properties.put(Constants.Y, y);
		properties.put(Constants.WIDTH, 1);
		properties.put(Constants.HEIGHT, 1);
		properties.put(Constants.HIT_POINTS, 15 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.HIT_POINTS_MAX, 15 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.NAME, "Skeleton");
		properties.put(Constants.ID, id);
		properties.put(Constants.IMAGE_ID, ImageIds.SKELETON);
		properties.put(Constants.ENERGY, 1000);
		properties.put(Constants.GROUP, new IdList().add(organization));
		properties.put(Constants.INVENTORY, new WorldObjectContainer());
		properties.put(Constants.GOLD, 0);
		properties.put(Constants.CREATURE_TYPE, CreatureType.SKELETON_CREATURE_TYPE);
		properties.put(Constants.CONDITIONS, new Conditions());
		properties.put(Constants.GOLD, 0);
		properties.put(Constants.CREATOR_ID, performer.getProperty(Constants.ID));
		properties.put(Constants.GIVEN_ORDER, null);
		
		properties.put(Constants.ARMOR, 10);
		
		properties.put(Constants.STRENGTH, 6);
		properties.put(Constants.DEXTERITY, 12);
		properties.put(Constants.CONSTITUTION, 8);
		properties.put(Constants.INTELLIGENCE, 6);
		properties.put(Constants.WISDOM, 12);
		properties.put(Constants.CHARISMA, 6);
		SkillUtils.addAllSkills(properties);
		
		properties.put(Constants.DAMAGE, 5 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.DAMAGE_RESIST, 10);
		
		WorldObject skeleton = new WorldObjectImpl(properties, Actions.ALL_ACTIONS, new SkeletonOnTurn(), new SkeletonWorldEvaluationFunction());
		world.addWorldObject(skeleton);
		
		return id;
	}
	
	public int generateFish(int x, int y, World world) {
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		int id = world.generateUniqueId();
		
		final String gender = generateGender();
		
		properties.put(Constants.X, x);
		properties.put(Constants.Y, y);
		properties.put(Constants.WIDTH, 1);
		properties.put(Constants.HEIGHT, 1);
		properties.put(Constants.HIT_POINTS, 2 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.HIT_POINTS_MAX, 3 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.NAME, "Fish");
		properties.put(Constants.ID, id);
		properties.put(Constants.IMAGE_ID, ImageIds.FISH_CREATURE);
		properties.put(Constants.FOOD, 1000);
		properties.put(Constants.FOOD_SOURCE, 1);
		properties.put(Constants.WATER, 1000);
		properties.put(Constants.ENERGY, 1000);
		properties.put(Constants.GROUP, new IdList().add(organization));
		properties.put(Constants.GOLD, 0);
		properties.put(Constants.DEMANDS, new Demands());
		properties.put(Constants.CHILDREN, new IdList());
		properties.put(Constants.SOCIAL, 500);
		properties.put(Constants.GENDER, gender);
		properties.put(Constants.CREATURE_TYPE, CreatureType.FISH_CREATURE_TYPE);
		properties.put(Constants.CONDITIONS, new Conditions());
		
		properties.put(Constants.ARMOR, 10);
		
		properties.put(Constants.DAMAGE, 2 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.DAMAGE_RESIST, 0);
		
		WorldObject fish = new WorldObjectImpl(properties, new FishOnTurn(this::generateFish));
		world.addWorldObject(fish);
		
		return id;
	}
	
	public int generateCow(int x, int y, World world) {
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		int id = world.generateUniqueId();
		
		final ImageIds imageId;
		final String gender;
		if (random.nextFloat() > 0.5f) {
			gender = "female";
			imageId = ImageIds.COW;
		} else {
			gender = "male";
			imageId = ImageIds.BULL;
		}
		
		properties.put(Constants.X, x);
		properties.put(Constants.Y, y);
		properties.put(Constants.WIDTH, 1);
		properties.put(Constants.HEIGHT, 1);
		properties.put(Constants.STRENGTH, 10);
		properties.put(Constants.HIT_POINTS, 4 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.HIT_POINTS_MAX, 5 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.NAME, "Cow");
		properties.put(Constants.ID, id);
		properties.put(Constants.IMAGE_ID, imageId);
		properties.put(Constants.LOOK_DIRECTION, LookDirection.SOUTH);
		properties.put(Constants.FOOD, 1000);
		properties.put(Constants.WATER, 1000);
		properties.put(Constants.ENERGY, 1000);
		properties.put(Constants.GROUP, new IdList().add(organization));
		properties.put(Constants.GOLD, 0);
		properties.put(Constants.DEMANDS, new Demands());
		properties.put(Constants.CHILDREN, new IdList());
		properties.put(Constants.SOCIAL, 500);
		properties.put(Constants.GENDER, gender);
		properties.put(Constants.CREATURE_TYPE, CreatureType.COW_CREATURE_TYPE);
		properties.put(Constants.CONDITIONS, new Conditions());
		properties.put(Constants.MEAT_SOURCE, 1);
		properties.put(Constants.ANIMAL_ENEMIES, new IdList());
		
		properties.put(Constants.ARMOR, 10);
		
		properties.put(Constants.DAMAGE, 2 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.DAMAGE_RESIST, 0);
		
		WorldObject cow = new WorldObjectImpl(properties, Actions.ALL_ACTIONS, new CowOnTurn(this::generateCow), new CowWorldEvaluationFunction());
		world.addWorldObject(cow);
		
		return id;
	}
	
	public int generateAnimatedSuitOfArmor(int x, int y, World world, WorldObject performer) {
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		int id = world.generateUniqueId();
		
		properties.put(Constants.X, x);
		properties.put(Constants.Y, y);
		properties.put(Constants.WIDTH, 1);
		properties.put(Constants.HEIGHT, 1);
		properties.put(Constants.HIT_POINTS, 15 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.HIT_POINTS_MAX, 15 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.NAME, "Animated Suit of Armor");
		properties.put(Constants.ID, id);
		properties.put(Constants.IMAGE_ID, ImageIds.ANIMATED_SUIT_OF_ARMOR);
		properties.put(Constants.FOOD, 1000);
		properties.put(Constants.WATER, 1000);
		properties.put(Constants.ENERGY, 1000);
		properties.put(Constants.GROUP, new IdList().add(organization));
		properties.put(Constants.GOLD, 0);
		properties.put(Constants.DEMANDS, new Demands());
		properties.put(Constants.CHILDREN, new IdList());
		properties.put(Constants.SOCIAL, 500);
		properties.put(Constants.CREATURE_TYPE, CreatureType.CONSTRUCT_CREATURE_TYPE);
		properties.put(Constants.CONDITIONS, new Conditions());
		properties.put(Constants.CREATOR_ID, performer.getProperty(Constants.ID));
		properties.put(Constants.GIVEN_ORDER, null);
		
		properties.put(Constants.ARMOR, 10);
		
		properties.put(Constants.DAMAGE, 2 * Item.COMBAT_MULTIPLIER);
		properties.put(Constants.DAMAGE_RESIST, 0);
		
		WorldObject construct = new WorldObjectImpl(properties, new ConstructOnTurn());
		world.addWorldObject(construct);
		
		return id;
	}
}
