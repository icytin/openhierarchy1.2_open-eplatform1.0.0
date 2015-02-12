package com.nordicpeak.saml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.binding.encoding.HTTPRedirectDeflateEncoder;
import org.opensaml.saml2.core.RequestAbstractType;
import org.opensaml.saml2.core.StatusResponseType;
import org.opensaml.util.URLBuilder;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.Pair;
import org.opensaml.xml.util.XMLHelper;


public class RedirectURLEncoder extends HTTPRedirectDeflateEncoder{

	protected Logger log = Logger.getLogger(this.getClass());

	public String buildRedirectURL(Credential signingCredential, String relayState, RequestAbstractType request, boolean logXML) throws MessageEncodingException {

		SAMLMessageContext<?, RequestAbstractType, ?> messageContext = new BasicSAMLMessageContext<SAMLObject, RequestAbstractType, SAMLObject>();
		messageContext.setOutboundSAMLMessage(request);
		messageContext.setRelayState(relayState);

		String messageStr = XMLHelper.nodeToString(marshallMessage(request));

		if (logXML && log.isInfoEnabled()){
			log.info("Generated SAML request: " + messageStr);
		}

		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		Deflater deflater = new Deflater(Deflater.DEFLATED, true);
		DeflaterOutputStream deflaterStream = new DeflaterOutputStream(bytesOut, deflater);
		try {
			deflaterStream.write(messageStr.getBytes("UTF-8"));
			deflaterStream.finish();
		} catch (IOException e) {
			throw new RuntimeException("Unable to deflate message", e);
		}

		String message = Base64.encodeBytes(bytesOut.toByteArray(), Base64.DONT_BREAK_LINES);

		//return super.buildRedirectURL(messageContext, request.getDestination(), encoded);

		//TODO this can be removed when Mobility guard has been upgraded
		//Code below is a snippet from OpenSAML 2.5.1 in order to override the call to queryParams.clear() a few lines down.

		URLBuilder urlBuilder = new URLBuilder(request.getDestination());

		List<Pair<String, String>> queryParams = urlBuilder.getQueryParams();
		//queryParams.clear();

		if (messageContext.getOutboundSAMLMessage() instanceof RequestAbstractType) {
			queryParams.add(new Pair<String, String>("SAMLRequest", message));
		} else if (messageContext.getOutboundSAMLMessage() instanceof StatusResponseType) {
			queryParams.add(new Pair<String, String>("SAMLResponse", message));
		} else {
			throw new MessageEncodingException("SAML message is neither a SAML RequestAbstractType or StatusResponseType");
		}

		if (checkRelayState(relayState)) {
			queryParams.add(new Pair<String, String>("RelayState", relayState));
		}

		if (signingCredential != null) {

			// Sign the parameters
			messageContext.setOutboundSAMLMessageSigningCredential(signingCredential);

			String sigAlgURI = getSignatureAlgorithmURI(signingCredential, null);
			Pair<String, String> sigAlg = new Pair<String, String>("SigAlg", sigAlgURI);
			queryParams.add(sigAlg);
			String sigMaterial = urlBuilder.buildQueryString();

			queryParams.add(new Pair<String, String>("Signature", generateSignature(signingCredential, sigAlgURI, sigMaterial)));
		}

		return urlBuilder.buildURL();
	}
}
