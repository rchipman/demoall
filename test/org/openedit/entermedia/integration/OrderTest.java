package org.openedit.entermedia.integration;

import java.util.Collection;

import org.openedit.Data;
import org.openedit.data.Searcher;
import org.openedit.entermedia.Asset;
import org.openedit.entermedia.BaseEnterMediaTest;
import org.openedit.entermedia.modules.OrderModule;
import org.openedit.entermedia.orders.Order;

import com.openedit.WebPageRequest;
import com.openedit.hittracker.HitTracker;
import com.openedit.hittracker.SearchQuery;
import com.openedit.page.Page;

public class OrderTest extends BaseEnterMediaTest
{

	public void testEmailOrder() throws Exception
	{
		WebPageRequest req = getFixture().createPageRequest("/testcatalog/views/orders/processorder.html");

		OrderModule om = (OrderModule)getModule("OrderModule");
		
		String catalogid = "entermedia/catalogs/testcatalog";
		Order order = om.getOrderManager().createNewOrder("testcatalog", catalogid, "admin");
		
		om.getOrderManager().saveOrder(catalogid, req.getUser(), order);

		Asset asset = getMediaArchive().getAsset("101");
		Data item = om.getOrderManager().addItemToBasket(catalogid, order, asset, null);	
		
		req.setRequestParameter("orderid", order.getId());

		//null req.setRequestParameter("presetid.value", "1");
		//req.setRequestParameter("publishdestination.value", "1");
		req.setRequestParameter("searchtype", "order");
		req.setRequestParameter("field", new String[]{ "publishdestination", "sharewithemail","sharesubject","sharenotes"}); //order stuff
		req.setRequestParameter("sharewithemail.value", "cburkey@openedit.org");
		req.setRequestParameter("sharesubject.value", "Test email");
		req.setRequestParameter("sharenotes.value", "Click on a link");
		
		req.setRequestParameter("itemid", item.getId());
		
		getFixture().getEngine().executePathActions(req);
		getFixture().getEngine().executePageActions(req);
		Thread.sleep(24000);
		order = om.getOrderManager().loadOrder(catalogid, order.getId());
		Collection items = om.getOrderManager().findOrderAssets(catalogid, order.getId());
		assertEquals(1, items.size());
		item = (Data)items.iterator().next();
		String emailsent = order.get("emailsent");
		
		assertEquals("true",emailsent);
		//orders are save in the data directory and there is an order and orderitem searcher
	}
	
	public void testPublishOrder() throws Exception
	{
		WebPageRequest req = getFixture().createPageRequest("/testcatalog/views/orders/processorder.html");

		OrderModule om = (OrderModule)getModule("OrderModule");
		
		String catalogid = "entermedia/catalogs/testcatalog";
		Order order = om.getOrderManager().createNewOrder("testcatalog", catalogid, "admin");
		
		om.getOrderManager().saveOrder(catalogid, req.getUser(), order);

		Asset asset = getMediaArchive().getAsset("101");
		Data item = om.getOrderManager().addItemToBasket(catalogid, order, asset, null);	
		
		req.setRequestParameter("orderid", order.getId());

		req.setRequestParameter("field", new String[]{ "publishdestination","presetid"}); //order stuff
		req.setRequestParameter("publishdestination.value", "1");
		req.setRequestParameter("searchtype", "order");

		req.setRequestParameter("itemid", item.getId());
		req.setRequestParameter(item.getId() + ".presetid.value", "2"); //outputffmpeg.avi
		
		getFixture().getEngine().executePathActions(req);
		getFixture().getEngine().executePageActions(req);
		
		Thread.sleep(12000);

		Page page = getPage("/WEB-INF/data/" + catalogid + "/generated/" + asset.getSourcePath() + "/outputffmpeg.avi");
		assertTrue(page.exists());
		
		order = om.getOrderManager().loadOrder(catalogid, order.getId());
		Collection items = om.getOrderManager().findOrderAssets(catalogid, order.getId());
		assertEquals(1, items.size());
		item = (Data)items.iterator().next();
		String emailsent = order.get("emailsent");
		
		assertEquals("true",emailsent);
		//orders are save in the data directory and there is an order and orderitem searcher
	}

	
	
	
	public void testPublishRhozetOrder() throws Exception
	{
		WebPageRequest req = getFixture().createPageRequest("/testcatalog/views/orders/processorder.html");

		OrderModule om = (OrderModule)getModule("OrderModule");
		
		String catalogid = "entermedia/catalogs/testcatalog";
		Order order = om.getOrderManager().createNewOrder("testcatalog", catalogid, "admin");
		
		om.getOrderManager().saveOrder(catalogid, req.getUser(), order);

		Asset asset = getMediaArchive().getAsset("101");
		Data item = om.getOrderManager().addItemToBasket(catalogid, order, asset, null);	
		
		req.setRequestParameter("orderid", order.getId());

		req.setRequestParameter("field", new String[]{ "publishdestination","presetid"}); //order stuff
		req.setRequestParameter("publishdestination.value", "1");
		req.setRequestParameter("searchtype", "order");

		req.setRequestParameter("itemid", item.getId());
		req.setRequestParameter(item.getId() + ".presetid.value", "rhozet-test"); //outputffmpeg.avi
		
		getFixture().getEngine().executePathActions(req);
		getFixture().getEngine().executePageActions(req);
		
		Thread.sleep(12000);

		Page page = getPage("/WEB-INF/data/" + catalogid + "/generated/" + asset.getSourcePath() + "/outputffmpeg.avi");
		assertTrue(page.exists());
		
		order = om.getOrderManager().loadOrder(catalogid, order.getId());
		Collection items = om.getOrderManager().findOrderAssets(catalogid, order.getId());
		assertEquals(1, items.size());
		item = (Data)items.iterator().next();
		String emailsent = order.get("emailsent");
		
		assertEquals("true",emailsent);
		//orders are save in the data directory and there is an order and orderitem searcher
	}
	
	
	
	
	
	public void xtestListOrders() throws Exception
	{
		OrderModule om = (OrderModule)getModule("OrderModule");
		WebPageRequest req = getFixture().createPageRequest("/entermedia/index.html");

		req.setRequestParameter("catalogid", "entermedia/catalogs/testcatalog");
		req.setRequestParameter("userid", "admin");
		HitTracker orders = om.findOrdersForUser(req);
		assertNotNull(orders);
		assertTrue(orders.size() > 0);
		Data order = (Data)orders.first();
	
		req.setRequestParameter("orderid", order.getId());
		HitTracker assets = om.findOrderAssets(req);
		assertNotNull(assets);
		assertTrue(assets.size() > 0);
	}
}
