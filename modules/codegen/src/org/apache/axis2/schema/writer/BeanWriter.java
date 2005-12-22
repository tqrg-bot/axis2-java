package org.apache.axis2.schema.writer;

import org.apache.axis2.schema.BeanWriterMetaInfoHolder;
import org.apache.axis2.schema.CompilerOptions;
import org.apache.axis2.schema.SchemaCompilationException;
import org.apache.ws.commons.schema.XmlSchemaComplexType;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaSimpleType;

import java.io.IOException;
import java.util.Map;
/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * The bean writer interface. The schema compiler expects one of these to be
 * presented to it and calls the appropriate methods
 */
public interface BeanWriter {


    /**
     * Init the write with compiler options
     *
     * @param options
     * @throws IOException
     */
    public void init(CompilerOptions options) throws SchemaCompilationException;

    /**
     * Writes a wrapped class. This will have effect only if the CompilerOptions wrapclassses
     * returns true
     */
    public void writeBatch() throws SchemaCompilationException;

    /**
     * Gets a map of models. this is useful for tight integrations where the internal workings
     * of the schema compiler may be exposed.
     */
    public Map getModelMap();

    /**
     * Write a complex type
     *
     * @param complexType
     * @param typeMap
     * @param metainf
     * @return
     * @throws SchemaCompilationException
     */
    public String write(XmlSchemaComplexType complexType, Map typeMap, BeanWriterMetaInfoHolder metainf) throws SchemaCompilationException;

    /**
     * Write a element
     *
     * @param element
     * @param typeMap
     * @param metainf
     * @return
     * @throws SchemaCompilationException
     */
    public String write(XmlSchemaElement element, Map typeMap, BeanWriterMetaInfoHolder metainf) throws SchemaCompilationException;


    /**
     * Write a simple type
     *
     * @param simpleType
     * @param typeMap
     * @param metainf
     * @return
     * @throws SchemaCompilationException
     */
    public String write(XmlSchemaSimpleType simpleType, Map typeMap, BeanWriterMetaInfoHolder metainf) throws SchemaCompilationException;

}
