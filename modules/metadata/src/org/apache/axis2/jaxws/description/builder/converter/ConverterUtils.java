package org.apache.axis2.jaxws.description.builder.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.jws.HandlerChain;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceRef;

import org.apache.axis2.jaxws.description.builder.DescriptionBuilderComposite;
import org.apache.axis2.jaxws.description.builder.FieldDescriptionComposite;
import org.apache.axis2.jaxws.description.builder.HandlerChainAnnot;
import org.apache.axis2.jaxws.description.builder.MethodDescriptionComposite;
import org.apache.axis2.jaxws.description.builder.ParameterDescriptionComposite;
import org.apache.axis2.jaxws.description.builder.SoapBindingAnnot;
import org.apache.axis2.jaxws.description.builder.TMAnnotationComposite;
import org.apache.axis2.jaxws.description.builder.TMFAnnotationComposite;
import org.apache.axis2.jaxws.description.builder.WebServiceRefAnnot;

public class ConverterUtils {
	
	/**
	 * Helper method to retrieve the annotation specified by a certain <code>Class</code>
	 * @param annotationClass - <code>Class</code> the annotation <code>Class</code>
	 * @param element - <code>AnnotatedElement</code> - the element on which we are 
	 * looking for the annotation (i.e. Class, Method, Field)
	 * @return - <code>Annotation</code> annotation represented by the given <code>Class</code>
	 */
	public static Annotation getAnnotation(Class annotationClass, AnnotatedElement element) {
		return element.getAnnotation(annotationClass);
	}
	/**
	 * This is a helper method to create a <code>HandlerChainAnnot</code> since the
	 * @HandlerChain annotation may be present on a Type, Method, or Field.
	 * @param handlerChain - <code>HandlerChain</code>
	 * @return - <code>HandlerChainAnnot</code>
	 */
	public static HandlerChainAnnot createHandlerChainAnnot(HandlerChain handlerChain) {
		HandlerChainAnnot hcAnnot = HandlerChainAnnot.createHandlerChainAnnotImpl();
		hcAnnot.setFile(handlerChain.file());
		hcAnnot.setName(handlerChain.name());
		return hcAnnot;
	}
	
	/**
	 * This is a helper method to create a <code>SoapBindingAnnot</code> since the
	 * @SOAPBinding annotation may be present on a Type or Method.
	 * @param soapBinding - <code>SOAPBinding</code>
	 * @return - <code>SoapBindingAnnot</code>
	 */
	public static SoapBindingAnnot createSoapBindingAnnot(SOAPBinding soapBinding) {
		SoapBindingAnnot sbAnnot = SoapBindingAnnot.createSoapBindingAnnotImpl();
		sbAnnot.setParameterStyle(soapBinding.parameterStyle());
		sbAnnot.setStyle(soapBinding.style());
		sbAnnot.setUse(soapBinding.use());
		return sbAnnot;
	}
	
	/**
	 * This is a helper method to create a <code>WebServiceRefAnnot</code> since the
	 * @WebServiceRef annotation may be present on a Type, Method, or Field.
	 * @param webServiceRef - <code>WebServiceRef</code>
	 * @return - <code>WebServiceRefAnnot</code>
	 */
	public static WebServiceRefAnnot createWebServiceRefAnnot(WebServiceRef webServiceRef) {
		WebServiceRefAnnot wsrAnnot = WebServiceRefAnnot.createWebServiceRefAnnotImpl();
		wsrAnnot.setMappedName(webServiceRef.mappedName());
		wsrAnnot.setName(webServiceRef.name());
		wsrAnnot.setType(webServiceRef.type());
		wsrAnnot.setValue(webServiceRef.value());
		wsrAnnot.setWsdlLocation(webServiceRef.wsdlLocation());
		return wsrAnnot;
	}
	
	/**
	 * This method is use to attach @HandlerChain annotation data to a composite 
	 * object.
	 * @param composite - <code>TMFAnnotationComposite</code>
	 * @param annotatedElement - <code>AnnotatedElement</code>
	 */
	public static void attachHandlerChainAnnotation(TMFAnnotationComposite composite, 
			AnnotatedElement annotatedElement) {
		HandlerChain handlerChain = (HandlerChain) ConverterUtils.getAnnotation(
				HandlerChain.class, annotatedElement);
		if(handlerChain != null) {
			HandlerChainAnnot hcAnnot = ConverterUtils.createHandlerChainAnnot(
					handlerChain);
			composite.setHandlerChainAnnot(hcAnnot);
		}
	}

	/**
	 * This method is use to attach @SOAPBinding annotation data to a composite 
	 * object.
	 * @param composite - <code>TMAnnotationComposite</code>
	 * @param annotatedElement - <code>AnnotatedElement</code>
	 */
	public static void attachSoapBindingAnnotation(TMAnnotationComposite composite, 
			AnnotatedElement annotatedElement) {
		SOAPBinding soapBinding = (SOAPBinding) ConverterUtils.getAnnotation(
				SOAPBinding.class, annotatedElement);
		if(soapBinding != null) {
			SoapBindingAnnot sbAnnot = ConverterUtils.createSoapBindingAnnot(soapBinding);
			composite.setSoapBindingAnnot(sbAnnot);
		}
	}
	
	/**
	 * This method is use to attach @WebServiceRef annotation data to a composite 
	 * object.
	 * @param composite - <code>TMFAnnotationComposite</code>
	 * @param annotatedElement - <code>AnnotatedElement</code>
	 */
	public static void attachWebServiceRefAnnotation(TMFAnnotationComposite composite, 
			AnnotatedElement annotatedElement) {
		WebServiceRef webServiceRef = (WebServiceRef) ConverterUtils.getAnnotation(
				WebServiceRef.class, annotatedElement);
		if(webServiceRef != null) {
			WebServiceRefAnnot wsrAnnot = ConverterUtils.createWebServiceRefAnnot(
					webServiceRef);
			composite.setWebServiceRefAnnot(wsrAnnot);
		}
	}
	
	/**
	 * This method will add FieldDescriptionComposite objects to a DescriptionBuilderComposite
	 */
	public static void attachFieldDescriptionComposites(DescriptionBuilderComposite 
			composite, List<FieldDescriptionComposite> fdcList) {
		for(FieldDescriptionComposite fdc : fdcList) {
			composite.addFieldDescriptionComposite(fdc);
		}
	}
	
	/**
	 * This method will add MethodDescriptionComposite objects to a DescriptionBuilderComposite
	 */
	public static void attachMethodDescriptionComposites(DescriptionBuilderComposite 
			composite, List<MethodDescriptionComposite> mdcList) {
		for(MethodDescriptionComposite mdc : mdcList) {
			composite.addMethodDescriptionComposite(mdc);
			mdc.setDescriptionBuilderCompositeRef(composite);
		}
	}
	
	/**
	 * This method will add ParameterDescriptionComposite objects to a MethodDescriptionComposite
	 */
	public static void attachParameterDescriptionComposites(List
			<ParameterDescriptionComposite> pdcList, MethodDescriptionComposite mdc) {
		for(ParameterDescriptionComposite pdc : pdcList) {
			mdc.addParameterDescriptionComposite(pdc);
			pdc.setMethodDescriptionCompositeRef(mdc);
		}
	}
	
	/**
	 * This method will check to see if a method's declaring class is the Object class.
	 * @param method - <code>Method</code>
	 * @return - <code>boolean</code>
	 */
	public static boolean isInherited(Method method, String declaringClass) {
		if(method.getDeclaringClass().getName().equals(declaringClass)) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method will construct a <code>String</code> that represents the
	 * full type of a parameterized variable.
	 * @param pt - <code>ParameterizedType</code>
	 * @param paramType - <code>String</code>
	 * @return - <code>String</code>
	 */
	public static String getFullType(ParameterizedType pt, String paramType) {
		if(pt.getRawType() instanceof Class) {
			Class rawClass = (Class) pt.getRawType();
			paramType = paramType + rawClass.getName();
		}
		Type[] genericTypes = pt.getActualTypeArguments();
		if(genericTypes.length > 0) {
			paramType = paramType + "<";
			for(int i=0; i < genericTypes.length; i++) {
				Type type = genericTypes[i];
				if(type instanceof Class) {
					if(i != genericTypes.length -1){
						paramType = paramType + ((Class) type).getName() + ", ";
					}
					else {
						paramType = paramType + ((Class) type).getName() + ">";
					}
				}
				if(type instanceof ParameterizedType) {
					paramType = getFullType((ParameterizedType)type, paramType) + ", ";
				}
			}
		}
		return paramType;
	}
}
