package fr.aresrpg.tofumanchou.domain.data.adapters;

import fr.aresrpg.commons.domain.reflection.ParametrizedClass;
import fr.aresrpg.commons.domain.serialization.adapters.Adapter;
import fr.aresrpg.tofumanchou.domain.data.SpellData.LangSpellProperty;
import fr.aresrpg.tofumanchou.domain.data.SpellData.ZoneEffect;

public class LangSpellPropertyAdapter implements Adapter<LangSpellProperty, Object[]> {

	public static final LangSpellPropertyAdapter INSTANCE = new LangSpellPropertyAdapter();
	private static final ParametrizedClass<LangSpellProperty> IN = new ParametrizedClass<>(LangSpellProperty.class);
	private static final ParametrizedClass<Object[]> OUT = new ParametrizedClass<>(Object[].class);

	@Override
	public Object[] adaptTo(LangSpellProperty in) {
		Object[] o = new Object[23];
		o[0] = in.getLvl();
		o[1] = in.getPaCost();
		o[2] = in.getRangeMin();
		o[3] = in.getRangeMax();
		o[4] = in.getCC();
		o[5] = in.getEC();
		o[6] = in.getClassid();
		o[7] = in.getLaunchByTurn();
		o[8] = in.getLaunchByPlayerTurn();
		o[9] = in.getDelayBetweenLaunch();
		o[10] = in.getMinPlayerLvl();
		o[11] = in.isLineOnly();
		o[12] = in.isLineOfSight();
		o[13] = in.isNeedFreeCell();
		o[14] = in.isPoModifiable();
		o[15] = in.isEcEndTurn();
		o[16] = in.isSummon();
		o[17] = in.isGlyph();
		o[18] = in.isTrap();
		o[19] = in.getRequiredStates();
		o[20] = in.getForbiddenStates();
		o[21] = in.getEffectsNormal();
		o[22] = in.getEffectsCritical();
		return o;
	}

	@Override
	public LangSpellProperty adaptFrom(Object[] out) {
		return new LangSpellProperty((int) out[0], (int) out[1], (int) out[2], (int) out[3], (int) out[4], (int) out[5], (int) out[6], (int) out[7], (int) out[8], (int) out[9], (int) out[10],
				(boolean) out[11], (boolean) out[12], (boolean) out[13], (boolean) out[14], (boolean) out[15], (boolean) out[16], (boolean) out[17], (boolean) out[18],
				(int[]) out[19], (int[]) out[20], (ZoneEffect[]) out[21], (ZoneEffect[]) out[22]);
	}

	@Override
	public ParametrizedClass<LangSpellProperty> getInType() {
		return IN;
	}

	@Override
	public ParametrizedClass<Object[]> getOutType() {
		return OUT;
	}

}