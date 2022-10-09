
package br.com.atividadepratica2b.model.dao;

import br.com.atividadepratica2b.model.entity.Produto;

public class ProdutoDAO extends ADAO<Produto>{

    public ProdutoDAO() {
        super(Produto.class);
    }
}
