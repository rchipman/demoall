package publishing.amazon

import java.util.Calendar

import org.entermedia.amazon.S3Repository
import org.openedit.Data
import org.openedit.data.Searcher
import org.openedit.entermedia.Asset
import org.openedit.entermedia.MediaArchive

import com.openedit.entermedia.scripts.EnterMediaObject

class amazon extends EnterMediaObject 
{
	public void loadSecureLink()
	{
		MediaArchive mediaArchive = context.getPageValue("mediaarchive");
		S3Repository repo = (S3Repository)mediaArchive.getModuleManager().getBean("S3Repository");
		Asset asset = context.getPageValue("asset");
		String presetid = context.findValue("presetid");
		String destinationid = context.findValue("destinationid");
		try
		{
			//Searcher presetsearcher = mediaArchive.getSearcherManager().getSearcher(mediaArchive.getCatalogId(), "convertpreset");
			Data destination = mediaArchive.getSearcherManager().getData( mediaArchive.getCatalogId(), "publishdestination", destinationid);
			Data preset = mediaArchive.getSearcherManager().getData( mediaArchive.getCatalogId(), "convertpreset", presetid);
			repo.setBucket(destination.bucket);
			repo.setAccessKey(destination.accesskey);
			repo.setSecretKey(destination.secretkey);
			
			String remotepath = mediaArchive.asExportFileName(asset,preset);
			
			boolean exists = repo.doesExist(remotepath);
			if( !exists)
			{
				context.putPageValue("error", "notfound");
				return;
			}
			
			Calendar now = Calendar.getInstance();
			now.add(now.HOUR_OF_DAY, 2);		
			String amazonstring = 	repo.getPresignedURL(remotepath, now.getTime());
			context.putPageValue("amazon", amazonstring);
		}
		catch( Exception ex)
		{
			context.putPageValue("error", "couldnotconnect");
			context.putPageValue("errordetails", ex.toString());
			
		}	
		//log.info(amazonstring);
		
	}
}
