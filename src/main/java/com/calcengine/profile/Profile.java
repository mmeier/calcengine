package com.calcengine.profile;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Individual Profile.
 */
public class Profile {

	private String id;
	private List<Attribute> attributes;
	private Map<Attribute, Integer> scores;

	public static Builder builder() {
		return new Builder();
	}

	/*
	 * Hide the constructor to force use of the Builder.
	 */
	private Profile() {
		attributes = new ArrayList<>();
		scores = new EnumMap<>(Attribute.class);
	}

	/**
	 * @return an Identifier unique to an individual
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the Attribute with the requested rank.
	 *
	 * @param rank Rank desired
	 * @return Attribute at that rank
	 */
	public Attribute getAttribute(int rank) {
		return attributes.get(rank - 1);
	}

	/**
	 * @return the ordered list of the Individual's attributes
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * Returns the rank of the Attribute
	 *
	 * @param attribute Attribute to get the Rank of
	 * @return the Rank (1 through 4 inclusive)
	 */
	public int getAttributeRank(Attribute attribute) {
		return attributes.indexOf(attribute) + 1;
	}

	/**
	 * Returns the Score of the Attribute
	 *
	 * @param attribute Attribute to get the score of
	 * @return the score
	 */
	public int getAttributeScore(Attribute attribute) {
		return scores.get(attribute);
	}

	/**
	 * Fluent builder for a Profile object that adheres to the Builder pattern.
	 */
	public static class Builder {
		private Profile profile;

		public Builder() {
			profile = new Profile();
		}

		public Builder withId(String id) {
			if (id == null || id.trim().isEmpty()) {
				throw new IllegalArgumentException("Profile must have an ID");
			}

			profile.id = id;
			return this;
		}

		public Builder withAttribute(Attribute attribute, int score) {
			validateAttribute(attribute, score);

			profile.attributes.add(attribute);
			profile.scores.put(attribute, score);

			return this;
		}

		public Profile build() {
			validateProfile();

			return profile;
		}

		private void validateAttribute(Attribute attribute, int score) {
			if (attribute == null) {
				throw new IllegalArgumentException("Attribute cannot be null");
			}

			if (score < 0) {
				throw new IllegalArgumentException("Score must be positive");
			}

			if (profile.scores.containsKey(attribute)) {
				throw new IllegalArgumentException("Duplicate Attribute, each Attribute must only be added once.");
			}
		}

		private void validateProfile() {
			if (profile.id == null) {
				throw new IllegalStateException("Profile is incomplete. ID is missing");
			}

			if (profile.attributes.size() < 4) {
				throw new IllegalStateException("Profile is incomplete. Ensure all Attributes have been added");
			}
		}
	}
}
