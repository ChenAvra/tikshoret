import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class message1 {
    private byte [] TeamName = new byte[32];
    private byte[] Type;
    private byte [] Hash = new byte[40];
    private byte[] OriginalLengh;
    private byte [] OriginalStringStart;
    private byte [] OrginalStringEnd;


    public message1(byte[] teamName, byte[] type, byte[] hash, byte[] originalLengh, byte[] originalStringStart, byte[] orginalStringEnd) {
        TeamName = teamName;
        Type = type;
        Hash = hash;
        OriginalLengh = originalLengh;
        OriginalStringStart = originalStringStart;
        OrginalStringEnd = orginalStringEnd;
    }
    public byte[] toByteArray() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(TeamName);
            byteArrayOutputStream.write(Type);
            byteArrayOutputStream.write(Hash);
            byteArrayOutputStream.write(OriginalLengh);
            if(OriginalStringStart!=null && OrginalStringEnd!=null){
                byteArrayOutputStream.write(OriginalStringStart);
                byteArrayOutputStream.write(OrginalStringEnd);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

       byte [] c = byteArrayOutputStream.toByteArray();
        return c;
    }

    public message1(byte [] byteArray){
        this.TeamName= Arrays.copyOfRange(byteArray,0,32);
        this.Type= Arrays.copyOfRange(byteArray,32,33);

        this.Hash= Arrays.copyOfRange(byteArray,33,73);

        this.OriginalLengh= Arrays.copyOfRange(byteArray,73,74);
        int size =Character.getNumericValue(OriginalLengh[0]);
        //int RangeLength = (byteArray.length-74)/2;
        this.OriginalStringStart= Arrays.copyOfRange(byteArray,74,74+size);
        this.OrginalStringEnd= Arrays.copyOfRange(byteArray,74+size,74+size+size);
    }

    public byte[] getTeamName() {
        return TeamName;
    }

    public byte[] getType() {
        return Type;
    }

    public byte[] getHash() {
        return Hash;
    }

    public byte[] getOriginalLengh() {
        return OriginalLengh;
    }

    public byte[] getOriginalStringStart() {
        return OriginalStringStart;
    }

    public byte[] getOrginalStringEnd() {
        return OrginalStringEnd;
    }
}
