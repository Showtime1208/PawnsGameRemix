package provider.model;

public interface ModelStatus {

  void yourTurn(); //notify whoever its turn it is when the model finishes making move for other player

  void gameOver(); //maybe notify both here

  void refreshView();
}
