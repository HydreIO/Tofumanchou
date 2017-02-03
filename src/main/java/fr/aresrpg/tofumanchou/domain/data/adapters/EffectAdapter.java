package fr.aresrpg.tofumanchou.domain.data.adapters;

import fr.aresrpg.commons.domain.reflection.ParametrizedClass;
import fr.aresrpg.commons.domain.serialization.adapters.Adapter;
import fr.aresrpg.dofus.structures.game.Effect;

public class EffectAdapter implements Adapter<Effect, Object[]> {

	public static final EffectAdapter INSTANCE = new EffectAdapter();
	private static final ParametrizedClass<Effect> IN = new ParametrizedClass<>(Effect.class);
	private static final ParametrizedClass<Object[]> OUT = new ParametrizedClass<>(Object[].class);

	@Override
	public Object[] adaptTo(Effect in) {
		Object[] o = new Object[7];
		o[0] = in.getTypeId();
		o[1] = in.getParam1();
		o[2] = in.getParam2();
		o[3] = in.getParam3();
		o[4] = in.getParam4();
		o[5] = in.getRemainingTurn();
		o[6] = in.getSpellId();
		return o;
	}

	@Override
	public Effect adaptFrom(Object[] out) {
		return new Effect((int) out[0], (int) out[1], (long) out[2], (int) out[3], (String) out[4], (int) out[5], (int) out[6]);
	}

	@Override
	public ParametrizedClass<Effect> getInType() {
		return IN;
	}

	@Override
	public ParametrizedClass<Object[]> getOutType() {
		return OUT;
	}

}