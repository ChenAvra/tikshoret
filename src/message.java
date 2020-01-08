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
       // System.out.println("in message class, your full string is:"+fullString);

        this.TeamName=(fullString.substring(0,32)).toCharArray();
        Type=fullString.charAt(32);



        this.Hash=(fullString.substring(33,73)).toCharArray();


        OriginalLengh=fullString.charAt(73);
        int sizeOfString = Character.getNumericValue((char)OriginalLengh);
        if(fullString.length()>74) {
            this.OriginalStringStart = (fullString.substring(74, 74 + sizeOfString)).toCharArray();
            this.OrginalStringEnd=(fullString.substring((74+sizeOfString),(74+sizeOfString+sizeOfString))).toCharArray();

        }


    }
    public String getFullString(){
        String toReturn="";
        String teamName = new String(this.getTeamName());
        String hash = new String (this.getHash());
        char TypeFromMessage = this.Type;
        char lenghFromMessage = this.OriginalLengh;

        int sizeOfString = Character.getNumericValue(OriginalLengh);
        String start="";
        String end="";
        if(getOriginalStringStart()!=null&& getOrginalStringEnd()!=null){
             start = new String(this.getOriginalStringStart());
            end = new String (this.getOrginalStringEnd());
            toReturn=teamName+TypeFromMessage+hash+ lenghFromMessage+ start+end;
        }
        else{
            toReturn=teamName+TypeFromMessage+hash+ lenghFromMessage;
        }


        return toReturn;

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
