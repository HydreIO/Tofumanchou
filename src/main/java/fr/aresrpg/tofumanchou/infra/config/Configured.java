package fr.aresrpg.tofumanchou.infra.config;

import java.lang.annotation.*;

/**
 * A configured field in {@link Config}
 * 
 * @author Duarte David {@literal <deltaduartedavid@gmail.com>}
 * @author Sceat {@literal <sceat@aresrpg.fr>}
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configured {
	/**
	 * <p>
	 * The path
	 * </p>
	 * Use dot for subpath ex : <code>@Configured(foo.bar.config)</code>
	 * 
	 * @return the path in the configuration
	 */
	String value() default "var.";
}
