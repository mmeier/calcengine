package com.calcengine;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProfileTest {

	@Test
	public void attributesAreOrderedAsBuilt() {
		Profile profile = Profile.builder()
				.withId("test")
				.withAttribute(Attribute.FOCUS, 100)
				.withAttribute(Attribute.DISCIPLINE, 75)
				.withAttribute(Attribute.ACCURACY, 70)
				.withAttribute(Attribute.DRIVE, 60)
				.build();

		assertThat(profile.getAttribute(1)).isEqualTo(Attribute.FOCUS);
		assertThat(profile.getAttribute(2)).isEqualTo(Attribute.DISCIPLINE);
		assertThat(profile.getAttribute(3)).isEqualTo(Attribute.ACCURACY);
		assertThat(profile.getAttribute(4)).isEqualTo(Attribute.DRIVE);
	}

	@Test
	public void attributeListReflectsCorrectOrder() {
		Profile profile = Profile.builder()
				.withId("test")
				.withAttribute(Attribute.DRIVE, 100)
				.withAttribute(Attribute.DISCIPLINE, 75)
				.withAttribute(Attribute.FOCUS, 70)
				.withAttribute(Attribute.ACCURACY, 60)
				.build();

		assertThat(profile.getAttributes()).containsExactly(
				Attribute.DRIVE,
				Attribute.DISCIPLINE,
				Attribute.FOCUS,
				Attribute.ACCURACY);
	}

	@Test
	public void attributeScoresRetrievedAccurately() {
		Profile profile = Profile.builder()
				.withId("test")
				.withAttribute(Attribute.DRIVE, 100)
				.withAttribute(Attribute.DISCIPLINE, 75)
				.withAttribute(Attribute.FOCUS, 70)
				.withAttribute(Attribute.ACCURACY, 60)
				.build();

		assertThat(profile.getAttributeScore(Attribute.ACCURACY)).isEqualTo(60);
		assertThat(profile.getAttributeScore(Attribute.DISCIPLINE)).isEqualTo(75);
		assertThat(profile.getAttributeScore(Attribute.DRIVE)).isEqualTo(100);
		assertThat(profile.getAttributeScore(Attribute.FOCUS)).isEqualTo(70);
	}

	@Test
	public void profileIdRequired() {
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
			Profile profile = Profile.builder()
					.withAttribute(Attribute.DRIVE, 100)
					.withAttribute(Attribute.DISCIPLINE, 75)
					.withAttribute(Attribute.FOCUS, 70)
					.withAttribute(Attribute.ACCURACY, 60)
					.build();
		});
	}

	@Test
	public void noDuplicateAttributes() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			Profile profile = Profile.builder()
					.withId("test")
					.withAttribute(Attribute.DRIVE, 100)
					.withAttribute(Attribute.DRIVE, 99)
					.build();
		});
	}

	@Test
	public void noNegativeScores() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			Profile profile = Profile.builder()
					.withId("test")
					.withAttribute(Attribute.FOCUS, -25)
					.withAttribute(Attribute.DISCIPLINE, 75)
					.withAttribute(Attribute.ACCURACY, 70)
					.withAttribute(Attribute.DRIVE, 60)
					.build();
		});
	}

	@Test
	public void allAttributesPresent() {
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
			Profile profile = Profile.builder()
					.withId("test")
					.withAttribute(Attribute.FOCUS, 100)
					.withAttribute(Attribute.DISCIPLINE, 75)
					.withAttribute(Attribute.ACCURACY, 70)
					.build();
		});
	}

	@Test
	public void idCannotBeBlank() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			Profile profile = Profile.builder()
					.withId(" ")
					.withAttribute(Attribute.FOCUS, -25)
					.withAttribute(Attribute.DISCIPLINE, 75)
					.withAttribute(Attribute.ACCURACY, 70)
					.withAttribute(Attribute.DRIVE, 60)
					.build();
		});
	}
}