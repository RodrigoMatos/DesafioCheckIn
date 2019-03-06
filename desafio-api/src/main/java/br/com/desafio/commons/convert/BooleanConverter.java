package br.com.desafio.commons.convert;

import javax.persistence.AttributeConverter;

public class BooleanConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean attribute) {
		if (attribute == null)
			return null;
		return (attribute) ? "S" : "N";
	}

	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		return "1".equals(dbData) || "S".equalsIgnoreCase(dbData) || "SIM".equalsIgnoreCase(dbData)
				|| "TRUE".equalsIgnoreCase(dbData);
	}
}
