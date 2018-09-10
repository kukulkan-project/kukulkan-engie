/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018 Roberto Villarejo Martínez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package mx.infotec.dads.kukulkan.engine.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.infotec.dads.kukulkan.engine.config.InflectorConf;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { InflectorServiceImpl.class })
@Import(InflectorConf.class)
public class InflectorServiceTest {

    @Autowired
    private InflectorService inflector;

    @Test
    public void singularizeTest() {
        assertEquals(null, inflector.singularize(null));
        assertEquals("UC", inflector.singularize("UC"));
        assertEquals("usuarioUC", inflector.singularize("usuarioUC"));
        assertEquals("persona", inflector.singularize("personas"));
        assertEquals("institucion", inflector.singularize("Instituciones"));
        assertEquals("emailPersonal", inflector.singularize("emailPersonal"));
        assertEquals("buyerOrganizationId", inflector.singularize("buyerOrganizationId"));
        assertEquals("UsuarioUc", inflector.singularize("UsuarioUc"));
        assertEquals("UsuarioUC", inflector.singularize("UsuarioUC"));
        assertEquals("Uc", inflector.singularize("Uc"));
        assertEquals("EstatusUc", inflector.singularize("EstatusUc"));
        assertEquals("PerfilUsuarioUC", inflector.singularize("PerfilUsuarioUC"));
        assertEquals("TipoContratacion", inflector.singularize("TipoContratacion"));
    }

    @Test
    public void pluralizeTest() {
        assertEquals("", inflector.pluralize(""));
        assertEquals(null, inflector.pluralize(null));
        assertEquals("UC", inflector.pluralize("UC"));
        assertEquals("usuarioUC", inflector.pluralize("usuarioUC"));
        assertEquals("personas", inflector.pluralize("persona"));
        assertEquals("instituciones", inflector.pluralize("Institucion"));
        assertEquals("emailPersonal", inflector.pluralize("emailPersonal"));
        assertEquals("buyerOrganizationId", inflector.pluralize("buyerOrganizationId"));
        assertEquals("UsuarioUc", inflector.pluralize("UsuarioUc"));
        assertEquals("UsuarioUC", inflector.pluralize("UsuarioUC"));
        assertEquals("Uc", inflector.pluralize("Uc"));
        assertEquals("EstatusUc", inflector.pluralize("EstatusUc"));
        assertEquals("PerfilUsuarioUC", inflector.pluralize("PerfilUsuarioUC"));
        assertEquals("TipoContratacion", inflector.pluralize("TipoContratacion"));
    }

}
