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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.worldgrower.Constants;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.ManagedProperty;
import org.worldgrower.generator.ItemType;
import org.worldgrower.gui.ImageIds;
import org.worldgrower.gui.debug.PropertiesModel.PropertyComparator;

public class InventoryItem {
	private final int id;
	private final String description;
	private final int price;
	private boolean sellable;
	private final ImageIds imageId;
	private final Map<String, String> additionalProperties;
	private final ItemType itemType;
	
	public InventoryItem(int id, WorldObject inventoryWorldObject) {
		if (inventoryWorldObject == null) {
			throw new IllegalStateException("inventoryWorldObject is null");
		}
		if (inventoryWorldObject.getProperty(Constants.PRICE) == null) {
			throw new IllegalStateException("inventoryWorldObject.price is null for " + inventoryWorldObject);
		}
		if (inventoryWorldObject.getProperty(Constants.SELLABLE) == null) {
			throw new IllegalStateException("inventoryWorldObject.sellable is null for " + inventoryWorldObject);
		}
		
		this.id = id;
		this.description = inventoryWorldObject.getProperty(Constants.NAME);
		this.price = inventoryWorldObject.getProperty(Constants.PRICE);
		this.imageId = inventoryWorldObject.getProperty(Constants.IMAGE_ID);
		this.sellable = inventoryWorldObject.getProperty(Constants.SELLABLE);
		this.itemType = inventoryWorldObject.getProperty(Constants.ITEM_ID).getItemType();
		
		this.additionalProperties = generateAdditionalProperties(inventoryWorldObject);
	}

	private static Map<String, String> generateAdditionalProperties(WorldObject inventoryWorldObject) {
		Map<String, String> additionalProperties = new LinkedHashMap<>();
		
		List<ManagedProperty<?>> propertyKeys = inventoryWorldObject.getPropertyKeys();
		Collections.sort(propertyKeys, new PropertyComparator());
		
		for(ManagedProperty<?> propertyKey : propertyKeys) {
			String name = propertyKey.getName().toLowerCase();
			if (propertyKey == Constants.DAMAGE 
					|| propertyKey == Constants.ARMOR 
					|| propertyKey == Constants.WEIGHT 
					|| propertyKey == Constants.QUANTITY
					|| propertyKey == Constants.LONG_DESCRIPTION
					|| Constants.isTool(propertyKey)
					) {
				String value = inventoryWorldObject.getProperty(propertyKey).toString();
				additionalProperties.put(name, value);
			} else if (propertyKey == Constants.EQUIPMENT_HEALTH) {
				String value = inventoryWorldObject.getProperty(propertyKey).toString();
				int intValue = Integer.parseInt(value);
				int percentage = (100 * intValue) / 1000;
				additionalProperties.put(name, percentage + "%");
			} else if (propertyKey == Constants.SELLABLE) {
				Boolean sellable = (Boolean) inventoryWorldObject.getProperty(propertyKey);
				additionalProperties.put(name, sellable ? "yes" : "no");
			} else if (propertyKey == Constants.TWO_HANDED_WEAPON) {
				Boolean twohHanded = (Boolean) inventoryWorldObject.getProperty(propertyKey);
				additionalProperties.put("two handed", twohHanded ? "yes" : "no");
			}
		}
		
		return additionalProperties;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}	
	
	public int getQuantity() {
		String quantityKey = Constants.QUANTITY.getName().toLowerCase();
		String quantity = additionalProperties.get(quantityKey);
		if (quantity != null) {
			return Integer.parseInt(quantity);
		} else {
			return 1;
		}
	}
	
	public int getWeight() {
		String weight = additionalProperties.get(Constants.WEIGHT.getName().toLowerCase());
		if (weight != null) {
			return Integer.parseInt(weight);
		} else {
			return 0;
		}
	}
	
	public int getPrice() {
		return price;
	}

	public boolean isSellable() {
		return sellable;
	}

	public void setSellable(boolean sellable) {
		this.sellable = sellable;
	}

	public ImageIds getImageId() {
		return imageId;
	}

	@Override
	public String toString() {
		return getDescription();
	}

	public Map<String, String> getAdditionalProperties() {
		return additionalProperties;
	}

	public boolean isWeapon() {
		return itemType == ItemType.WEAPON;
	}

	public boolean isArmor() {
		return itemType == ItemType.ARMOR;
	}
	
	public boolean isPotion() {
		return itemType == ItemType.DRINK;
	}
	
	public boolean isFood() {
		return itemType == ItemType.FOOD;
	}

	public boolean isBook() {
		return itemType == ItemType.BOOK;
	}

	public boolean isKey() {
		return itemType == ItemType.KEY;
	}

	public boolean isResource() {
		return itemType == ItemType.RESOURCE;
	}

	public boolean isAlchemyIngredient() {
		return itemType == ItemType.INGREDIENT;
	}
	
	public boolean isTool() {
		return itemType == ItemType.TOOL;
	}

	public String getAttack() {
		return additionalProperties.get(Constants.DAMAGE.getName().toLowerCase());
	}
	
	public String getArmor() {
		return additionalProperties.get(Constants.ARMOR.getName().toLowerCase());
	}

	public String getToolBonus() {
		for(ManagedProperty<?> property : Constants.getToolProperties()) {
			String value = additionalProperties.get(property.getName().toLowerCase());
			if (value != null) {
				return value;
			}
		}
		throw new IllegalStateException("No tool bonus was found in " + additionalProperties);
	}

	public String getLongDescription() {
		return additionalProperties.get(Constants.LONG_DESCRIPTION.getName().toLowerCase());
	}
}