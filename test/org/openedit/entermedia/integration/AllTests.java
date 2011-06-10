package org.openedit.entermedia.integration;


import java.util.Arrays;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.openedit.entermedia.controller.ArchiveModuleTest;
import org.openedit.entermedia.controller.MultiSearchModuleTest;
import org.openedit.entermedia.model.AlbumTest;
import org.openedit.entermedia.model.AssetEditTest;
import org.openedit.entermedia.model.CategoryEditTest;
import org.openedit.entermedia.model.ConvertionTest;
import org.openedit.entermedia.model.FriendTest;
import org.openedit.entermedia.model.MetaDataReaderTest;
import org.openedit.entermedia.model.RelatedAssetsTest;
import org.openedit.entermedia.model.SourcePathTest;
import org.openedit.entermedia.model.ThesaurusTest;
import org.openedit.entermedia.model.VideoConvertionTest;
import org.openedit.entermedia.model.ZipTest;
import org.openedit.entermedia.view.ConvertDocumentGeneratorTest;
import org.openedit.entermedia.view.OriginalDocumentGeneratorTest;

import com.openedit.util.Exec;
import com.openedit.util.ExecResult;

public class AllTests {
	public static Test suite()
	{
		TestSuite suite = new TestSuite( "Test for entermedia" );
		
		suite.addTestSuite( OrderTest.class );

//		
		return suite;
	}
}
