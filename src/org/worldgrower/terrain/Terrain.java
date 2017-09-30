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
package org.worldgrower.terrain;

import java.awt.Rectangle;

/**
 * A Terrain represents the state of the terrain in a world.
 * What terrain type it is and whether it is explored.
 */
public interface Terrain {

	public TerrainType getTerrainType(int x, int y);
	public int getWidth();
	public int getHeight();
	
	public boolean isExplored(int x, int y);
	public void explore(int x, int y, int radius); 
	public Rectangle getExploredBoundsInSquares();
	
	public default boolean isExplored(int x, int y, int width, int height) {
		return isExplored(x, y)
			|| isExplored(x + width - 1, y)
			|| isExplored(x, y + height - 1)
			|| isExplored(x + width - 1, y + height - 1);
	}
}