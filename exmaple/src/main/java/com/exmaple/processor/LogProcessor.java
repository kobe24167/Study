package com.exmaple.processor;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.exmaple.annotation.Log;

@SupportedAnnotationTypes({"com.exmaple.annotation.Log"})
@ConfigurationProperties
public class LogProcessor implements AbstractProcessor {
	@Override
	public synchronized void init(ProcessingEnvironment env) {
	};

	@Override
	public boolean process(Set<? extends TypeElement> annoations, RoundEnvironment env) {
		for (Element annotatedElement : env.getElementsAnnotatedWith(Log.class)) {
			System.out.println(annotatedElement.toString());
	    }
		return false;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		// TODO Auto-generated method stub
		return null;
	};
}
