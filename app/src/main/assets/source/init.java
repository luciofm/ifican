    // Configura a posição e tamanhos iniciais da Imagem
    // das daquis iremos animar para a posição final...
    image.setPivotX(0);
    image.setPivotY(0);
    image.setScaleX(widthScale);
    image.setScaleY(heightScale);
    image.setTranslationX(leftDelta);
    image.setTranslationY(topDelta);

    // Depois animaremos o conteúdo...
    text1.setAlpha(0);