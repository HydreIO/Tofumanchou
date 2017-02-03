package fr.aresrpg.tofumanchou.domain.data.adapters;

import fr.aresrpg.commons.domain.reflection.ParametrizedClass;
import fr.aresrpg.commons.domain.serialization.adapters.Adapter;
import fr.aresrpg.dofus.structures.game.Effect;
import fr.aresrpg.tofumanchou.domain.data.SpellData.ZoneEffect;
import fr.aresrpg.tofumanchou.domain.data.enums.Element;

public class ZoneEffectAdapter implements Adapter<ZoneEffect, Object[]> {

	public static final ZoneEffectAdapter INSTANCE = new ZoneEffectAdapter();
	private static final ParametrizedClass<ZoneEffect> IN = new ParametrizedClass<>(ZoneEffect.class);
	private static final ParametrizedClass<Object[]> OUT = new ParametrizedClass<>(Object[].class);

	@Override
	public Object[] adaptTo(ZoneEffect in) {
		Object[] o = new Object[4];
		o[0] = in.getEffect();
		o[1] = in.getElement();
		o[2] = in.getShape();
		o[3] = in.getZone();
		return o;
	}

	@Override
	public ZoneEffect adaptFrom(Object[] out) {
		return new ZoneEffect((Effect) out[0], (Element) out[1], (int) out[2], (int) out[3]);
	}

	@Override
	public ParametrizedClass<ZoneEffect> getInType() {
		return IN;
	}

	@Override
	public ParametrizedClass<Object[]> getOutType() {
		return OUT;
	}

}