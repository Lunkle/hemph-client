package logics.globe;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Biomes {

	FOREST(0, false),
	PLAINS(1, false),
	SWAMP(2, false),
	JUNGLE(3, false),
	OCEAN(4, true),
	DESERT(5, false),
	MOSSY(6, false),
	h(7, false),
	i(8, false),
	LAKE(9, true),
	k(10, false),
	TROPICAL_LAKE(11, true),
	m(12, false),
	n(13, false),
	o(14, false),
	p(15, true);

	private static Map<Integer, Biomes> biomeMap;
	private static List<Integer> landBiomeIndices;
	private static List<Integer> waterBiomeIndices;

	private int index;
	private boolean isWater;

	static {
		biomeMap = new HashMap<>();
		landBiomeIndices = new ArrayList<>();
		waterBiomeIndices = new ArrayList<>();
		for (Biomes biome : EnumSet.allOf(Biomes.class)) {
			biomeMap.put(biome.index, biome);
			if (biome.isWater) {
				waterBiomeIndices.add(biome.index);
			} else {
				landBiomeIndices.add(biome.index);
			}
		}
	}

	private Biomes(int index, boolean isWater) {
		this.index = index;
		this.isWater = isWater;
	}

	public int getIndex() {
		return index;
	}

	public static Biomes getBiome(int biomeIndex) {
		return biomeMap.get(biomeIndex);
	}

	public static int numberOfBiomes() {
		return biomeMap.keySet().size();
	}

	public static List<Integer> getLandBiomeIndices() {
		return landBiomeIndices;
	}

	public static List<Integer> getWaterBiomeIndices() {
		return waterBiomeIndices;
	}

}
