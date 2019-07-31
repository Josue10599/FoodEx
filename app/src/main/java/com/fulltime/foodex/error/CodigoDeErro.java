package com.fulltime.foodex.error;

/**
 * FoodEx is a sales management application.
 * Copyright (C) 2019 Josue Lopes.
 * <p>
 * FoodEx is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * FoodEx is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

public interface CodigoDeErro {

    int ERRO_DELETAR_CLIENTE = 0;
    int ERRO_DELETAR_PRODUTO = 1;
    int ERRO_ADICIONAR_PRODUTO = 2;
    int ERRO_ADICIONAR_CLIENTE = 3;
    int ERRO_ADICIONAR_VENDA = 4;
    int ERRO_ADICIONAR_PAGAMENTO = 5;
    int ERRO_FALHA_CONEXAO = 6;
}
