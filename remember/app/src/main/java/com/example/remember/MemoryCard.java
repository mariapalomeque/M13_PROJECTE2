package com.example.remember;

class MemoryCard {
    private final int identifier;
    private boolean isFaceUp;
    private boolean isMatched;

    public MemoryCard(int identifier) {
        this.identifier = identifier;
        this.isFaceUp = false;
        this.isMatched = false;
    }

    public int getIdentifier() {
        return identifier;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }
}

