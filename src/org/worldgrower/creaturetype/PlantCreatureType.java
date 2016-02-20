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
package org.worldgrower.creaturetype;

import java.io.ObjectStreamException;
import java.util.List;

public class PlantCreatureType implements CreatureType {

	public PlantCreatureType(List<CreatureType> allCreatureTypes) {
		allCreatureTypes.add(this);
	}

	@Override
	public boolean canTalk() {
		return false;
	}

	@Override
	public boolean canMove() {
		return false;
	}
	
	@Override
	public boolean canTrade() {
		return false;
	}
	
	@Override
	public boolean hasBlood() {
		return false;
	}
	
	public Object readResolve() throws ObjectStreamException {
		return readResolveImpl();
	}
}
