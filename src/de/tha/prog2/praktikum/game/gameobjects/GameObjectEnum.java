package de.tha.prog2.praktikum.game.gameobjects;

public enum GameObjectEnum {
    BLAST("Blast"),
    BOMB("Bomb"),
    HAND_OPERATED_BOMBERMAN("HandOperatedBomberman"),
    BOMBERMAN("Bomberman"),
    DESTRUCTIBLE_WALL("DestructibleWall"),
    EXTEND_BOMB_STRENGTH("ExtendBombStrength"),
    EXTRA_POINTS("ExtraPoints"),
    MORE_BOMBS("MoreBombs"),
    MONSTER("Monster"),
    POWER_UP("PowerUp"),
    WALL("Wall");

    private final String simpleName;

    GameObjectEnum(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    // Gibt passendes Enum zu SimpleName zurück und null, wenn es keins gibt:
    public static GameObjectEnum getEnum(String simpleName) {
        for (GameObjectEnum type : values()) {
            if (type.simpleName.equals(simpleName)) {
                return type;
            }
        }
        return null;
    }
}
