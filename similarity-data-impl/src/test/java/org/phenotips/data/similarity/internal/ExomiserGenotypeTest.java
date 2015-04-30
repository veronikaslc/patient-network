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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.phenotips.data.similarity.internal;

import org.phenotips.data.similarity.Genotype;
import org.phenotips.data.similarity.Variant;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the {@link ExomiserGenotype} implementation based on the latest Exomiser-3.0.2 output file format
 *
 * @version $Id$
 */
public class ExomiserGenotypeTest
{
    private static final String TEST_FILE =
        "#CHROM\tPOS\tREF\tALT\tQUAL\tFILTER\tGENOTYPE\tCOVERAGE\tFUNCTIONAL_CLASS\tHGVS\tEXOMISER_GENE\tCADD(>0.483)\tPOLYPHEN(>0.956|>0.446)\tMUTATIONTASTER(>0.94)\tSIFT(<0.06)\tDBSNP_ID\tMAX_FREQUENCY\tDBSNP_FREQUENCY\tEVS_EA_FREQUENCY\tEVS_AA_FREQUENCY\tEXOMISER_VARIANT_SCORE\tEXOMISER_GENE_PHENO_SCORE\tEXOMISER_GENE_VARIANT_SCORE\tEXOMISER_GENE_COMBINED_SCORE\n" +
        "chr16\t30748691\tC\tT\t225.0\tPASS\t0/1\t40\tSTOPGAIN\tSRCAP:uc002dzg.1:exon29:c.6715C>T:p.R2239*\tSRCAP\t.\t.\t.\t.\t.\t0.0\t.\t.\t.\t0.95\t0.8603835\t0.95\t0.9876266\n" +
        "chr1\t120611964\tG\tC\t76.0\tPASS\t0/1\t42\tMISSENSE\tNOTCH2:uc001eil.3:exon1:c.57C>G:p.C19W\tNOTCH2\t6.292\t.\t.\t0.0\t.\t0.0\t.\t.\t.\t1.0\t0.7029731\t1.0\t0.9609373\n" +
        "chr6\t32628660\tT\tC\t225.0\tPASS\t0/1\t94\tSPLICING\tHLA-DQB1:uc031snx.1:exon5:c.773-1A>G\tHLA-DQB1\t.\t.\t.\t.\t.\t0.0\t.\t.\t.\t0.9\t0.612518\t1.0\t0.9057237\n";
    /** Basic test for Exomiser output file parsing. */
    @Test
    public void testParseExomiser()
    {
        Genotype genotype = null;
        try {
            genotype = new ExomiserGenotype(new StringReader(TEST_FILE));
        } catch (IOException e) {
            Assert.fail("Exomiser file parsing resulted in IOException");
        }
        
        Assert.assertEquals(0.9876266, genotype.getGeneScore("SRCAP"), 0.00001);
        
        // Get top variant and make sure it was parsed correctly
        Variant v1 = genotype.getTopVariant("SRCAP", 0);
        Assert.assertEquals("STOPGAIN", v1.getEffect());
        Assert.assertEquals("16", v1.getChrom());
        Assert.assertEquals((Integer)30748691, v1.getPosition());
        Assert.assertEquals("C", v1.getRef());
        Assert.assertEquals("T", v1.getAlt());
        Assert.assertEquals("40", v1.getAnnotation("COVERAGE"));
        Assert.assertFalse(v1.isHomozygous());

        // Try to get second (non-existent) variant
        Variant v2 = genotype.getTopVariant("SRCAP", 1);
        Assert.assertNull(v2);
        
        Assert.assertEquals(3, genotype.getGenes().size());
    }
}
