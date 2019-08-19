package logics.globe;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Biomes {

	FOREST(0),
	b(1),
	c(2),
	d(3),
	OCEAN(4),
	DESERT(5),
	g(6),
	h(7),
	i(8),
	LAKE(9),
	k(10),
	l(11),
	m(12),
	n(13),
	o(14),
	p(15);

	private static Map<Integer, Biomes> biomeMap;

	private int index;

	static {
		biomeMap = new HashMap<>();
		for (Biomes biome : EnumSet.allOf(Biomes.class)) {
			biomeMap.put(biome.index, biome);
		}
	}

	private Biomes(int index) {
		this.index = index;
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

}
