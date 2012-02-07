package org.dy.oauth.qq.demo.web;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.QqzoneApi;
import org.scribe.extractors.TokenExtractorImpl;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class OauthQqzoneServlet extends HttpServlet {
	String appId;
	String appSrt;
	String callbackUrl;

	OAuthService service;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getParameter("login") != null) {
			Token reqToken = service.getRequestToken();
			// 保存secret到session以备后面调用open api时候用
			req.getSession().setAttribute("s_srt", reqToken.getSecret());
			// 先去Qqzone登陆
			String authUrl = service.getAuthorizationUrl(reqToken)
					+ "&oauth_callback=" + callbackUrl + "&oauth_consumer_key="
					+ appId;
			resp.sendRedirect(authUrl);
		} else if (req.getParameter("oauth_token") != null) {
			// callback回来了
			Token reqToken = new Token(req.getParameter("oauth_token"),
					(String) req.getSession().getAttribute("s_srt"));
			Verifier verifier = new Verifier(req.getParameter("oauth_vericode"));
			Token accessToken = service.getAccessToken(reqToken, verifier);
			Pattern p = Pattern.compile("openid=([^&]+)");
			;
			String openid = TokenExtractorImpl.extract(accessToken
					.getRawResponse(), p);

			String urlGetOpenidInfo = "http://openapi.qzone.qq.com/user/get_user_info";
			OAuthRequest treq = new OAuthRequest(Verb.GET, urlGetOpenidInfo);
			treq.addQuerystringParameter("openid", openid);
			service.signRequest(accessToken, treq);

			Response oauthResp = treq.send();
			String result=oauthResp.getBody();
			// TODO with response
//			resp.getWriter().write(oauthResp.getBody());
//			resp.getWriter().flush();
			 req.setAttribute("result", result);
			 req.getRequestDispatcher("/login_success.jsp").forward(req,
			 resp);
			// { "ret":0, "msg":"", "nickname":"堔凊sh１嗨╮", "figureurl":"http://qzapp.qlogo.cn/qzapp/100237107/614DB49744A7E872BC13A1F3C0BF80BF/30", "figureurl_1":"http://qzapp.qlogo.cn/qzapp/100237107/614DB49744A7E872BC13A1F3C0BF80BF/50", "figureurl_2":"http://qzapp.qlogo.cn/qzapp/100237107/614DB49744A7E872BC13A1F3C0BF80BF/100", "gender":"男", "vip":"0", "level":"0" }
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		appId = config.getInitParameter("app-id");
		appSrt = config.getInitParameter("app-srt");
		callbackUrl = config.getInitParameter("callback-url");

		service = new ServiceBuilder().provider(QqzoneApi.class).apiKey(appId)
				.apiSecret(appSrt).callback(callbackUrl).signatureType(
						SignatureType.QueryString).build();
	}

}
