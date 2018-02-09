package com.calcengine.calc;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CodingTest {

	@Test
	public void verifyForwardCoding() {
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(Coding.forward("1")).isEqualTo(0.0);
		softly.assertThat(Coding.forward("2")).isEqualTo(0.25);
		softly.assertThat(Coding.forward("3")).isEqualTo(0.5);
		softly.assertThat(Coding.forward("4")).isEqualTo(0.75);
		softly.assertThat(Coding.forward("5")).isEqualTo(1.0);
		softly.assertThat(Coding.forward(null)).isEqualTo(0.0);

		softly.assertAll();
	}

	@Test
	public void verifyBackwardCoding() {
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(Coding.backward("1")).isEqualTo(1.0);
		softly.assertThat(Coding.backward("2")).isEqualTo(0.75);
		softly.assertThat(Coding.backward("3")).isEqualTo(0.5);
		softly.assertThat(Coding.backward("4")).isEqualTo(0.25);
		softly.assertThat(Coding.backward("5")).isEqualTo(0.0);
		softly.assertThat(Coding.backward(null)).isEqualTo(0.0);

		softly.assertAll();
	}

}