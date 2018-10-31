package testes.bancodedados;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.bancodedados.bancoDB;

class TesteBC {

	@Test
	void test() {
		
		
		bancoDB bd= new bancoDB();
		
		bd.insertUSer("1", "222");
		System.out.println();
		assertEquals(1,bd.getSizeTableUser() );
		
	
	}

}
