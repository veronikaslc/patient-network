/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/
 */
package org.phenotips.data.similarity;

import org.phenotips.data.Disorder;

import org.xwiki.stability.Unstable;

/**
 * View of a disorder as related to another reference disorder.
 *
 * @version $Id$
 * @since 1.0M1
 */
@Unstable
public interface DisorderSimilarityView extends Disorder
{
    /**
     * Does this similar disorders pair have both a match and a reference?
     *
     * @return {@code true} if both related disorders are present, {@code false} otherwise
     */
    boolean isMatchingPair();

    /**
     * Returns the reference disorder matched by this disease, if any.
     *
     * @return a disorder from the reference patient, or {@code null} if this disorder doesn't match a reference
     *         disorder
     */
    Disorder getReference();

    /**
     * How similar is this disorder to the reference.
     *
     * @return a similarity score, between {@code -1} for opposite disorders and {@code 1} for an exact match, with
     *         {@code 0} for disorders with no similarities, and {@code NaN} in case there's no matched reference
     */
    double getScore();
}
