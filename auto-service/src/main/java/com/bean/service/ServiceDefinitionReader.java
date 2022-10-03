package com.bean.service;

public interface ServiceDefinitionReader<S, D extends ServiceDefinition<S> > {
   D read(Class<S> clazz);
}
