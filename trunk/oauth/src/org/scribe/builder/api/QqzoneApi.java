package org.scribe.builder.api;

import org.scribe.model.Token;
import org.scribe.model.Verb;

public class QqzoneApi extends DefaultApi10a {
	static final String AUTHORIZATION_URL = "http://openapi.qzone.qq.com/oauth/qzoneoauth_authorize?oauth_token=%s";

	public String getAccessTokenEndpoint(){
		return "http://openapi.qzone.qq.com/oauth/qzoneoauth_access_token";
	}

	public String getRequestTokenEndpoint(){
		return "http://openapi.qzone.qq.com/oauth/qzoneoauth_request_token";
	}

	public Verb getAccessTokenVerb(){
		return Verb.GET;
	}

	public Verb getRequestTokenVerb(){
		return Verb.GET;
	}

	public String getAuthorizationUrl(Token requestToken) {
		return String.format(AUTHORIZATION_URL, requestToken.getToken());
	}
	
}
