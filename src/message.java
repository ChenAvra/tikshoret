public class message {
    private char [] TeamName = new char[32];
    private char Type;
    private char [] Hash = new char[40];
    private char OriginalLengh;
    private char [] OriginalStringStart;
    private char [] OrginalStringEnd;


    public message(char[] teamName, char type, char[] hash, char originalLengh, char[] originalStringStart, char[] orginalStringEnd) {
        TeamName = teamName;
        Type = type;
        Hash = hash;
        OriginalLengh = originalLengh;
        OriginalStringStart = originalStringStart;
        OrginalStringEnd = orginalStringEnd;
    }
    public message(String fullString){

        int indexOfFullString=0;
        for(int i=0; i<32; i++){
            this.TeamName[i]=fullString.charAt(indexOfFullString);
            indexOfFullString++;
        }
        Type=fullString.charAt(indexOfFullString);
        indexOfFullString++;

        for(int i=0; i<40; i++){
            this.Hash[i]=fullString.charAt(indexOfFullString);
            indexOfFullString++;
        }
        OriginalLengh=fullString.charAt(indexOfFullString);
        indexOfFullString++;

        int sizeOfString = (int) Math.pow(2, getOriginalLengh());

        for(int i=0; i<sizeOfString; i++){
            this.OriginalStringStart[i]=fullString.charAt(indexOfFullString);
            indexOfFullString++;
        }

        for(int i=0; i<sizeOfString; i++){
            this.OrginalStringEnd[i]=fullString.charAt(indexOfFullString);
            indexOfFullString++;
        }

    }

    public char[] getTeamName() {
        return TeamName;
    }

    public char getType() {
        return Type;
    }

    public char[] getHash() {
        return Hash;
    }

    public char getOriginalLengh() {
        return OriginalLengh;
    }

    public char[] getOriginalStringStart() {
        return OriginalStringStart;
    }

    public char[] getOrginalStringEnd() {
        return OrginalStringEnd;
    }
}
