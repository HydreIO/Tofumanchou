package fr.aresrpg.tofumanchou.infra.db;

import fr.aresrpg.commons.domain.serialization.Format;
import fr.aresrpg.commons.domain.serialization.SerializationContext;
import fr.aresrpg.commons.domain.types.TypeEnum;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.Document;

/**
 * 
 * @since
 */
public class GsonFormat implements Format<Document, Document> {

	public static final Gson GSON = new GsonBuilder().serializeNulls().create();
	public static final GsonFormat INSTANCE = new GsonFormat();

	@Override
	public void writeBegin(Document out) throws IOException {

	}

	@Override
	public void writeValue(Document out, String name, TypeEnum type, Object value, SerializationContext context) throws IOException {
		out.put(name, GSON.toJson(value));
	}

	@Override
	public void writeBeginObject(Document out) throws IOException {

	}

	@Override
	public void writeFieldSeparator(Document out, boolean firstField, boolean lastField) throws IOException {

	}

	@Override
	public void writeEndObject(Document out) throws IOException {

	}

	@Override
	public void writeEnd(Document out) throws IOException {

	}

	@Override
	public Object read(Document in) throws IOException {
		return in.toJson();
	}

}
