package pt.ist.bankai.command.converter;

import pt.ist.bankai.domain.MarionetteViewType;

import com.beust.jcommander.IStringConverter;

public class MarionetteViewTypeConverter implements IStringConverter<MarionetteViewType> {

	public MarionetteViewType convert(String value) {
		return MarionetteViewType.fromString(value);
	}

}
