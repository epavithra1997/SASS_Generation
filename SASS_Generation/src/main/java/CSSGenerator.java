import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sun.java.accessibility.util.java.awt.ButtonTranslator;

public class CSSGenerator {
    private HashMap<String, ElementDetails> elementDetails;

    CSSGenerator(HashMap<String, ElementDetails> elementDetails){
        this.elementDetails = elementDetails;
    }
    ArrayList<String> css =new ArrayList<>();
    Map<String,String> css_map = new HashMap<String,String>();
    static int j;
    String str=" ";
  	 String strkey=" ";
  	 String keyPrint=" ";
    int keysize,flag1=0,flag2=0;
    String mixinprefix="";
    String functionprefix="";
    String buttonprefix="";
    String include="@include cssstyles(";
    String include1="@include imgstyles(";


    public String builtCSSFormatContent(){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder11 = new StringBuilder();
        StringBuilder functioncall=new StringBuilder();
        ArrayList<String> textattribute=new ArrayList<>();
        ArrayList<String> imgattribute=new ArrayList<>();
        StringBuilder mixin=new StringBuilder();
        StringBuilder buttonbg=new StringBuilder();
        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder12 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder finalstring=new StringBuilder();
        buttonbg.append("$button:(");

        elementDetails.forEach((key,value)->{
               if(value.getCssStyles().size() == 0){
                return;
            }

                 if(true)
                  for (Map.Entry<String, String> entry : value.getCssStyles().entrySet()) {
                	  String str=entry.getValue();
                      if(css.contains(str))
                      {
                                    css_map.put(str,("var_"+Integer.toString(j)));
                                    j++;
                      }
                      else
                      {
                                    css.add(str);
                      }




                                  key=entry.getKey();
                                  if(value.getType().equals("text") && !textattribute.contains(key)){
                                                  textattribute.add(key);

                                  }
                                  else if(value.getType().equals("image") && !imgattribute.contains(key))
                                  {
                                                  imgattribute.add(key);
                                  }
                  }


        });

        mixin.append("@mixin cssstyles(");
        for (String temp :textattribute) {

                                                mixin.append(mixinprefix).append("$").append(temp).append(": null");
                                                mixinprefix=", ";

        }
        mixin.append(") {\n");
        for (String temp :textattribute) {
                                                mixin.append(temp).append(":").append("#{$").append(temp).append("}; \n");

                                }
        mixin.append("}\n");

        mixinprefix="";
        mixin.append("@mixin imgstyles(");
        for (String temp :imgattribute) {
                                                mixin.append(mixinprefix).append("$").append(temp).append(": null");
                                                mixinprefix=", ";
                                }
        mixin.append(") {\n");
        for (String temp :imgattribute) {
                                                mixin.append(temp).append(":").append("#{$").append(temp).append("}; \n");

                                }
        mixin.append("}\n");

        elementDetails.forEach((key, value) -> {
            keysize=value.getCssStyles().size();

         if( keysize==0 ){



             return;
         }

            if(value.getType().equals("text"))
            {

            stringBuilder.append(".").append(value.getName()).append("{").append("\n").append(include);
            flag1=1;
            for (Map.Entry<String, String> entry : value.getCssStyles().entrySet()) {
                int flag3=0;
                 for (Map.Entry<String,String> entry1 : css_map.entrySet())
                                               	{

                                               		if(entry1.getKey().equals(entry.getValue()))
                                               		{
                                               			flag3=1;
                                               			str=entry1.getValue();
                                               			strkey=entry.getKey();

                                               		}
                                               		if(flag3==1)
                                               		{
                                               			break;
                                               		}
                                               	}

                                               	if(flag3==1)
                                               	{
                                               		//System.out.println("text@@@@@@@@@"+strkey+str+value.getName());
                                               		functioncall.append(functionprefix).append("$").append(strkey).append(":").append("$").append(str);
                                                               functionprefix=", ";
                                               	}

                                               	else
                                               	{
                                               		//System.out.println("text#########"+entry.getKey()+entry.getValue());
                                               	functioncall.append(functionprefix).append("$").append(entry.getKey()).append(":").append(entry.getValue());
                                                               functionprefix=", ";
                                               	}


               functionprefix=",";
            }
            functionprefix="";
            functioncall.append(");");
            stringBuilder.append(functioncall);
           functioncall.setLength(0);
            stringBuilder.append("\n }").append("\n\n");
            }  // text heading nav---------------close

            else if (value.getType().equals("button")) {
                                                                int size=value.getCssStyles().size();
                                                               flag2=1;
                                                                int flag=0;
                                                                if(size==1)
                                                                {
                                                             for (Map.Entry<String, String> entry : value.getCssStyles().entrySet()) {
                                               keysize =entry.getKey().length();
                                               if(keysize==16)
                                               {
                                               	for (Map.Entry<String,String> entry1 : css_map.entrySet())
                                               	{

                                               		if(entry1.getKey().equals(entry.getValue()))
                                               		{
                                               			flag=1;
                                               			str=entry1.getValue();

                                               		}
                                               		if(flag==1)
                                               		{
                                               			break;
                                               		}
                                               	}
                                               }
                                               	if(flag==1)
                                               	{
                                               		//System.out.println("button@@@@@@@@@"+value.getName()+str);
                                               		buttonbg.append(buttonprefix).append("\"").append(value.getName()).append("\"").append(":").append("$").append(str);
                                                               buttonprefix=", ";
                                               	}

                                               	else
                                               	{
                                               		//System.out.println("button##########"+value.getName()+entry.getValue());
                                               	buttonbg.append(buttonprefix).append("\"").append(value.getName()).append("\"").append(":(").append(entry.getValue()).append(");");
                                                               buttonprefix=", ";
                                               	}
                                               }
                                               }

                 else
               {
                                stringBuilder1.append(".").append(value.getName()).append("{").append("\n");

                                for (Map.Entry<String, String> entry : value.getCssStyles().entrySet()) {
                                	 int flag11=0;
                                	 for (Map.Entry<String,String> entry1 : css_map.entrySet())
                                               	{

                                               		if(entry1.getKey().equals(entry.getValue()))
                                               		{
                                               			flag11=1;
                                               			str=entry1.getValue();
                                               			strkey=entry.getKey();

                                               		}
                                               		if(flag11==1)
                                               		{
                                               			break;
                                               		}
                                               	}

                                               	if(flag11==1)
                                               	{
                                               		//System.out.println("@@@@@@@@@"+strkey+str);
                                               		stringBuilder1.append("\t").append(strkey).append(" : ").append(str).append(";").append("\n");
                                               	}

                                               	else
                                               	{
                                               		//System.out.println("############"+entry.getKey()+entry.getValue());
                                               	stringBuilder1.append("\t").append(entry.getKey()).append(" : ").append(entry.getValue()).append(";").append("\n");
                                               	}

                                               }

                                stringBuilder1.append("\n }").append("\n\n");


                                }



        }

            else if(value.getType().equals("image"))
            {

                stringBuilder1.append(".").append(value.getName()).append("{").append("\n").append(include1);


                 for (Map.Entry<String, String> entry : value.getCssStyles().entrySet()) {
                	 int flag=0;

                                               	for (Map.Entry<String,String> entry1 : css_map.entrySet())
                                               	{

                                               		if(entry1.getKey().equals(entry.getValue()))
                                               		{
                                               			flag=1;
                                               			str=entry1.getValue();
														strkey=entry.getKey();
                                               		}
                                               		if(flag==1)
                                               		{
                                               			break;
                                               		}
                                               	}

                                               	if(flag==1)
                                               	{
                                               		//System.out.println("image@@@@@@@@@"+strkey+str);
                                               		functioncall.append(functionprefix).append("$").append(strkey).append(":(").append("$").append(str).append(")");
                                               		functionprefix=",";
                                               	}

                                               	else
                                               	{
                                               	//System.out.println("image##########"+entry.getKey()+entry.getValue());
                                               	functioncall.append(functionprefix).append("$").append(entry.getKey()).append(":(").append(entry.getValue()).append(")");
                                               	functionprefix=",";
                                               	}


                 }
                 functionprefix="";
                 functioncall.append(");");
                 stringBuilder1.append(functioncall);
                functioncall.setLength(0);
                 stringBuilder1.append("\n }").append("\n\n");
                }

            else
            {
               stringBuilder2.append(".").append(value.getName()).append("{").append("\n");

                                for (Map.Entry<String, String> entry : value.getCssStyles().entrySet()) {
                                	System.out.println("cssstyles"+value.getCssStyles());
                                    int flag4=0;
                                	for (Map.Entry<String,String> entry1 : css_map.entrySet())
                                               	{

                                               		if(entry1.getKey().equals(entry.getValue()))
                                               		{
                                               			flag4=1;
                                               			str=entry1.getValue();
                                               			strkey=entry.getKey();
                                               			//System.out.println("error00000000000"+strkey+str);
                                               			break;
                                               		}


                                               	}

                                               	if(flag4==1)
                                               	{
                                               		//System.out.println("extra@@@@@@@@@"+strkey+"\n nameeeee\t"+value.getName()+str);
                                               		stringBuilder2.append("\t").append(strkey).append(" : ").append("$").append(str).append(";").append("\n");
                                               	}

                                               	else
                                               	{
                                               		//System.out.println("extra##########"+entry.getKey()+entry.getValue());
                                               	stringBuilder2.append("\t").append(entry.getKey()).append(" : ").append(entry.getValue()).append(";").append("\n");
                                               	}


                                }

                                stringBuilder2.append("\n }").append("\n\n");

            }

        });
        buttonbg.append(");");
        buttonbg.append("\n @each $name, $color in $button { \n").append(".").append("#{$name} { \n").append("background-color : ").append("$color;").append("\n }").append("\n}\n");
        Iterator<Map.Entry<String, String>> itr = css_map.entrySet().iterator();

        while(itr.hasNext())
        {
             Map.Entry<String, String> entryvar = itr.next();
            System.out.println("Key = " + entryvar.getKey() +
                                 ", Value = " + entryvar.getValue());
            String strAppend= "$"+entryvar.getValue()+": "+entryvar.getKey()+";";

            stringBuilder11.append(strAppend).append("\n");

        }
        mixin.append(stringBuilder11);
        mixin.append(buttonbg);
        if(flag1==1)
        mixin.append(stringBuilder);
        if(flag2==1)
        mixin.append(stringBuilder1);
        mixin.append(stringBuilder12);
        mixin.append(stringBuilder2);
        return mixin.toString();

    }
}
