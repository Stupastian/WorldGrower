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
package org.worldgrower.gui.inventory;

import java.awt.Image;

import org.worldgrower.Constants;
import org.worldgrower.DungeonMaster;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.actions.MarkInventoryItemAsSellableAction;
import org.worldgrower.attribute.Demands;
import org.worldgrower.attribute.Prices;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.goal.WeightPropertyUtils;
import org.worldgrower.gui.ImageIds;
import org.worldgrower.gui.ImageInfoReader;
import org.worldgrower.gui.WorldPanel;
import org.worldgrower.gui.music.SoundIdReader;
import org.worldgrower.gui.start.Game;

public class InventoryDialogModel {

	private final WorldObject playerCharacter;
	private final WorldObject target;
	private final World world;
	private final DungeonMaster dungeonMaster;
	private final WorldPanel parent;
	private final ImageInfoReader imageInfoReader;
	private final SoundIdReader soundIdReader;
	
	public InventoryDialogModel(WorldObject playerCharacter, World world, DungeonMaster dungeonMaster, WorldPanel parent, ImageInfoReader imageInfoReader, SoundIdReader soundIdReader) {
		this.playerCharacter = playerCharacter;
		this.target = null;
		this.world = world;
		this.dungeonMaster = dungeonMaster;
		this.parent = parent;
		this.imageInfoReader = imageInfoReader;
		this.soundIdReader = soundIdReader;
	}
	
	public InventoryDialogModel(WorldObject playerCharacter, WorldObject target, World world, DungeonMaster dungeonMaster, WorldPanel parent, ImageInfoReader imageInfoReader, SoundIdReader soundIdReader) {
		this.playerCharacter = playerCharacter;
		this.target = target;
		this.world = world;
		this.dungeonMaster = dungeonMaster;
		this.parent = parent;
		this.imageInfoReader = imageInfoReader;
		this.soundIdReader = soundIdReader;
	}

	public int getPlayerCharacterMoney() {
		return playerCharacter.getProperty(Constants.GOLD);
	}
	
	public String getPlayerCharacterName() {
		return playerCharacter.getProperty(Constants.NAME);
	}
	
	public int getPlayerCharacterWeight() {
		return WeightPropertyUtils.getTotalWeight(playerCharacter);
	}
	
	public int getPlayerCharacterCarryingCapacity() {
		return WeightPropertyUtils.getCarryingCapacity(playerCharacter);
	}
	
	public boolean hasTargetMoney() {
		return target.hasProperty(Constants.GOLD);
	}
	
	public int getTargetMoney() {
		return target.getProperty(Constants.GOLD);
	}
	
	public boolean targetHasMoneyToSteal() {
		return target.getProperty(Constants.GOLD).intValue() > 0; 
	}
	
	public String getTargetName() {
		return target.getProperty(Constants.NAME);
	}
	
	public boolean hasTargetCarryingCapacity() {
		return target.hasProperty(Constants.STRENGTH);
	}
	
	public int getTargetWeight() {
		return WeightPropertyUtils.getTotalWeight(target);
	}
	
	public int getTargetCarryingCapacity() {
		return WeightPropertyUtils.getCarryingCapacity(target);
	}
	
	public Image getPlayerCharacterImage(ImageInfoReader imageInfoReader) {
		ImageIds imageIdPerformer = playerCharacter.getProperty(Constants.IMAGE_ID);
		return imageInfoReader.getImage(imageIdPerformer, null);
	}
	
	public Image getTargetImage(ImageInfoReader imageInfoReader) {
		ImageIds imageIdPerformer = target.getProperty(Constants.IMAGE_ID);
		return imageInfoReader.getImage(imageIdPerformer, null);
	}
	
	public WorldObjectContainer getPlayerCharacterInventory() {
		return playerCharacter.getProperty(Constants.INVENTORY);
	}
	
	public WorldObjectContainer getTargetInventory() {
		return target.getProperty(Constants.INVENTORY);
	}
	
	public boolean hasTarget() {
		return target != null;
	}

	public Prices getPlayerCharacterPrices() {
		return playerCharacter.getProperty(Constants.PRICES);
	}

	public void markAsSellable(InventoryItem inventoryItem, boolean sellable) {
		int[] args = MarkInventoryItemAsSellableAction.createArgs(inventoryItem.getId(), sellable);
		Actions.MARK_INVENTORY_ITEM_AS_SELLABLE_ACTION.execute(playerCharacter, playerCharacter, args, world);
		
	}

	public Demands getPlayerCharacterDemands() {
		return playerCharacter.getProperty(Constants.DEMANDS);
	}
	
	public void setPrices(int[] args) {
		Game.executeAction(playerCharacter, Actions.SET_PRICES_ACTION, args, world, dungeonMaster, playerCharacter, parent, imageInfoReader, soundIdReader);
	}
}
