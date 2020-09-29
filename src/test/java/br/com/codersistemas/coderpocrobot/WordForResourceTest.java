package br.com.codersistemas.coderpocapachepoi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WordForResourceTest {
	
	@Test
	void test() throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("#GILIEDOUSUARIO", "GITE/PO??x");
		map.put("#NUMEROATESTE", "45654565");
		map.put("#GILOG", "7876");
		map.put("#NOME-FORNECEDOR", "FULANO CORPORATION");
		map.put("#CNPJ-FORNECEDOR", "443534534543543");
		map.put("#VLRPAGAMENTO", "123,56");
		map.put("#COMPETENCIA", "01/2020");
		map.put("#NUMEROCOMPROMISSO", "98989");
		map.put("#CNPJ-FORNECEDOR", "1313131312");
		map.put("#TEXTOSISFIN", "SDADADAS");
		map.put("#MULTA", "");
		map.put("#1", "X");
		map.put("#2", "");
		map.put("#3", "");
		map.put("#4", "");
		map.put("#CIDADE", "PORTO ALEGRE");
		map.put("#DD", "26");
		map.put("#MES", "AGOSTO");
		map.put("#ANO", "2020");
		WordForResource wordForResource = new WordForResource("modelo.docx", map);
		
		FileOutputStream fos = new FileOutputStream(new File("/home/gustavo/teste/modelo-ok.docx"));
		wordForResource.getDoc().write(fos);

	}

}
