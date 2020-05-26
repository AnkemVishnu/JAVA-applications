package com.tracfone.soa.client.impl.scriptmanagementservices;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import com.tracfone.b2b.scriptservice.GetScriptRequestType;
import com.tracfone.b2b.scriptservice.GetScriptResponseType;
import com.tracfone.b2b.scriptservice.ScriptService;
import com.tracfone.b2b.scriptservice.ScriptService_Service;
import com.tracfone.commontypes.LanguageType;
import com.tracfone.commontypes.TracfoneBrandType;
import com.tracfone.soa.client.api.scriptmanagementservices.ScriptServices;
import com.tracfone.soa.client.exception.SOAServiceException;
import com.tracfone.soa.handler.HeaderHandlerResolver;

public class SampleService implements ScriptServices {

	private ScriptService_Service _service = null;
	private String serviceLocationURL;

	public String getServiceLocationURL() {
		return serviceLocationURL;
	}

	public void setServiceLocationURL(String serviceLocationURL) {
		this.serviceLocationURL = serviceLocationURL;
	}

	public void setScriptService(ScriptService_Service scriptService) {
		this._service = scriptService;
	}

	private ScriptService createScriptServiceProxyStub() throws ServiceException {
		URL url = null;
		try {
			url = new URL(serviceLocationURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		_service = _service != null ? _service : new ScriptService_Service(url);
		_service.setHandlerResolver(new HeaderHandlerResolver());
		return _service.getScriptServiceSOAP();
	}

	@Override
	public GetScriptResponseType getScripts(String sourceSystem, String brandName, String lang, List<String> scriptIDs) throws ServiceException, RemoteException, SOAServiceException {
		if(scriptIDs.size() < 1){
			throw new ServiceException("Invalid SCRIPTIDSIZE");
		}
		ScriptService port = createScriptServiceProxyStub();
		GetScriptRequestType request = buildRequest(sourceSystem, brandName, lang, scriptIDs);
		return port.getScript(request);
	}

	private GetScriptRequestType buildRequest(String sourceSystem, String brandName, String lang, List<String> scriptIDs) {
		GetScriptRequestType request = new GetScriptRequestType();
		request.setBrandName(TracfoneBrandType.fromValue(brandName));
		request.setSourceSystem(sourceSystem);
		request.setLanguage(LanguageType.fromValue(lang));
		request.getScriptID().addAll(scriptIDs);
		return request;
	}

}
