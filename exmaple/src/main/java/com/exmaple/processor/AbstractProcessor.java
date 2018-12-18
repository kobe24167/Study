package com.exmaple.processor;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

public interface AbstractProcessor {

	boolean process(Set<? extends TypeElement> annoations, RoundEnvironment env);

	Set<String> getSupportedAnnotationTypes();

	void init(ProcessingEnvironment env);

	SourceVersion getSupportedSourceVersion();
}
