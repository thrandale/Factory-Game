package structures;

public class Belt extends Structure {

    public Belt(int x, int y, int rotation, int structureType, int id) {
        super(x, y, structureType, rotation, 4, id);
    }

    public void setType(int type, int rotation) {
        this.structureType = type;
        this.rotation = rotation;
    }
}
