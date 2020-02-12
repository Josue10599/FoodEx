# FoodEx <img align="right" src="https://github.com/Josue10599/FoodEx/blob/master/image/icon/Icon_FoodEx.svg" width="150" height="150"/>
FoodEx é uma aplicação que busca auxiliar e facilitar o gerenciamento de vendas, se voltando principalmente para pessoas autônomas que buscam ter maior controle de suas vendas.

A criação da aplicação ficou voltada para a venda de produtos alimentícios, porém ela possui o potencial para trabalhar com qualquer tipo de produto já que a mesma não possui uma limitação de tipo ou de conteúdo que pode ser adicionado.

Como é uma aplicação que possui o seu código aberto ela pode ser modificada para atuar com as suas necessidades, mas é claro que deve se atentar as limitações da licença que regem esse projeto [GPL-v3](https://github.com/Josue10599/FoodEx/blob/master/COPYING.md).

# Contribuir :+1:
Essas instruções irão facilitar a você que deseja estudar ou auxiliar por essa implementação.

## Pré-requisitos
1. Android Studio
2. Emulador ou Dispositivo Android
3. Firebase

## Configurando o Projeto
Para contribuir com o desenvolvimento da aplicação faça o **fork** da mesma, abra o projeto no
_Android Studio_ clique em **Make Project** ou pressione `CTRL+F9` para realizar a configuração pelo 
Gradle.

## Branch
Ao criar uma nova implementação, basta criar uma nova **branch** colocando como nome o objetivo da modificação
de forma resumida solicitar um **Pull-Request** acrescentando uma descrição mais profunda dos dados e dos processos
adicionados.

> Qualquer problema ou dificuldade basta acrescentar uma Issue com a tag HELP_GRADLE

## Configurando o Firebase

1. Adicionando o Firebase ao projeto

Nesta etapa irei deixar o link para a documentação do Google para o [Firebase](https://firebase.google.com/docs/android/setup?hl=pt-br#manually_add_firebase).

2. Firebase Authentication

Para configurar o Authentication vá em **Método de Login** e selecione *E-mail/Senha* e *Google*.

3. Firebase Cloud Firestore

Aqui devemos ir em **Regras** e adicionar as seguintes condições dentro de *service **cloud.firestore***:
```
  match /databases/{database}/documents {
  	match /usuario/{usuario} {
    		allow read, update, delete: if request.auth.uid == usuario
        	allow create: if request.auth.uid != null
      		match /clientes/{clientes} {
	  		allow read, create, update, delete: if request.auth.uid == usuario; 
    		}
    		match /produtos/{produtos} {
      	  		allow read, create, update, delete: if request.auth.uid == usuario; 
    		}
	    	match /vendas/{vendas} {
      		  allow read, create, delete: if request.auth.uid == usuario;
    		}
	        match /pagamentos/{pagamentos} {
        	  allow read, create, delete: if request.auth.uid == usuario;
	        }
        	match /empresa/{empresa} {
	      	  allow read, create, update, delete: if request.auth.uid == usuario;
        	}
     	}
  }
```

**NOTA:** A aplicação já está configurada para utilizar as funções de Crashlytics e Performance do Firebase.

> Qualquer problema ou dificuldade basta acrescentar uma Issue com a tag HELP_FIREBASE

# API de Terceiros

Abaixo se encontra as bibliotecas e as API de terceiros utilizadas juntamente de sua página no GitHub.
* [EventBus](https://github.com/greenrobot/EventBus)
* [Material Components](https://github.com/material-components)
* [Searchable Spinner](https://github.com/michaelprimez/searchablespinner)
* [Text Drawable](https://github.com/amulyakhare/TextDrawable)
* [Universal Image Loader](https://github.com/nostra13/Android-Universal-Image-Loader)
* [Firebase](https://github.com/firebase/quickstart-android)

# O conhecimento não deve ser privado!!!
