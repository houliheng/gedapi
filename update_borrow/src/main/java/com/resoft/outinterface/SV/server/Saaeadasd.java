package com.resoft.outinterface.SV.server;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import com.resoft.outinterface.SV.server.entry.request.SVRequest;
import com.resoft.outinterface.themis.entry.response.ThemisResponse;

public class Saaeadasd {
	static SchemaOutputResolver re = new SchemaOutputResolver() {
		
		@Override
		public Result createOutput(String arg0, String arg1) throws IOException {
			File f = new File("C://","ThemisResponse.xsd");
			return new StreamResult(f);
		}
	};
	public static void main(String[] args) {
		try {
			JAXBContext con = JAXBContext.newInstance(ThemisResponse.class);
			con.generateSchema(re);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
