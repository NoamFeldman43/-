import java.util.*;
import java.sql.*;

// מחלקה לנתוני קורס עם שדות לשם, קוד ומחלקה.
class CourseData {
	String name, code, department;// שם הקורס, קוד הקורס והמחלקה שלו.
	String[] stgrp, ins;// מערכים של קבוצות סטודנטים ומרצים המשויכים לקורס.
	int nostgrp, noins;// מונים לכמות קבוצות הסטודנטים והמרצים.
	public CourseData() {// בנאי לאתחול הנתונים.
		name = new String();
		code = new String();
		department = new String();
		stgrp = new String[100];
		ins = new String[100];
		nostgrp = 0;
		noins = 0;
	}
}

// מחלקה לנתוני קבוצת סטודנטים עם מידע וקוד הקבוצה, כוח הקבוצה ורשימת הקורסים.
class StudentGrpData {
	String info, code;// מידע כללי על הקבוצה וקוד הקבוצה.
	int strength;// מספר הסטודנטים בקבוצה.
	String[] course;// מערך של קורסים של קבוצת הסטודנטים.
	public StudentGrpData() {// בנאי לאתחול הנתונים.
		info = new String();
		code = new String();
		course = new String[100];
	}
}

// מחלקה לנתוני מרצה עם שם, קוד ורשימת קורסים שהוא מלמד.
class InstructorData {
	String name,code;// שם המרצה וקוד המרצה.
	String[] course;// מערך של קורסים שהמרצה מלמד.
	public InstructorData() {// בנאי לאתחול הנתונים.
		name = new String();
		code = new String();
		course = new String[10];
	}
}

// מחלקה לנתוני כיתה עם מחלקה, קוד וכושר הכיתה.
class ClassData {
	String department, code;// מחלקה של הכיתה וקוד הכיתה.
	int strength;// כוח הכיתה, כלומר מספר המקומות בכיתה.
	public ClassData() {// בנאי לאתחול הנתונים.
		department = new String();
		code = new String();
	}
}

class InputData {
	CourseData[] course;//מערך של נתוני קורסים.
	ClassData[] rooms;	//מערך של נתוני כיתות.
	StudentGrpData[] stgrp;//מערך של נתוני קבוצות סטודנטים.
	InstructorData[] ins;//מערך של נתוני מורים.
	int noroom, nocourse, nostgrp, noins;//מונים של כיתות, קורסים, קבוצות סטודנטים ומורים.
	public InputData() {
		course = new CourseData[100];//
		rooms = new ClassData[100];//
		stgrp = new StudentGrpData[100];//
		ins = new InstructorData[100];//
	}

	boolean classFormat(String l) {
		StringTokenizer st = new StringTokenizer(l," ");
		if(st.countTokens()==3)
			return(true);
		else
			return(false);
	}

	public void takeInput() {// טוען נתונים ממסד נתונים.
		//Time complexity: O(n), כאשר n הוא מספר השורות במסד הנתונים.
		Connection cn=null;
		Statement st=null;
		ResultSet rs=null;

		try {
			int i=0, j, k;
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			cn= DriverManager.getConnection("jdbc:ucanaccess://C://Users//noamj//Desktop//timetable-generation-system-master//bin//data.accdb");
			st=cn.createStatement();

			rs=st.executeQuery("SELECT * FROM course");
			while (rs.next()) {					
				course[i]=new CourseData();
				course[i].name=rs.getString("cname");
				course[i].code=rs.getString("ccode");
				course[i].department=rs.getString("cdep");
				i++;
			}
			nocourse=i;

			// ביצוע שאילתה SQL כדי לקבל את כל הרשומות מטבלת studentgrp
			rs=st.executeQuery("SELECT * FROM studentgrp");
			i=0;// אתחול מונה
			// לולאה שרצה כל עוד יש רשומות נוספות בתוצאת השאילתה
			while(rs.next()) {
				stgrp[i]=new StudentGrpData();   // יצירת אובייקט חדש של StudentGrpData לכל רשומה
				// הגדרת המאפיינים של האובייקט עם הנתונים מהשאילתה
				stgrp[i].info=rs.getString("info");
				stgrp[i].code=rs.getString("code");
				stgrp[i].strength=Integer.parseInt(rs.getString("strength"));
				// שמירת רשימת קורסים שהקבוצה נרשמה אליהם
				stgrp[i].course[0]=rs.getString("sub1");
				stgrp[i].course[1]=rs.getString("sub2");
				stgrp[i].course[2]=rs.getString("sub3");
				stgrp[i].course[3]=rs.getString("sub4");
				stgrp[i].course[4]=rs.getString("sub5");

				// לולאה לפי מספר הקורסים שהקבוצה נרשמה אליהם
				for(j=0;j<5;j++) {
					// בדיקה במערך הקורסים איזה קורס מתאים לקורס שנמצא ברשימת הקורסים של הקבוצה
					for(k=0;k<nocourse;k++) {
						// אם נמצא קורס מתאים, הוספת קוד הקבוצה לרשימת הקבוצות הרשומות לקורס זה
						if(course[k].code.equals(stgrp[i].course[j]))
							course[k].stgrp[course[k].nostgrp++]=stgrp[i].code;
					}
				}
				i++;
			}
			// שמירת כמות הקבוצות שנטענו
			nostgrp=i;

			// ביצוע שאילתה SQL לקבלת כל הרשומות מטבלת instructors
			rs=st.executeQuery("SELECT * FROM instructors");
			i=0;// איתחול מונה
			// לולאה שקוראת כל רשומה מתוצאות השאילתה
			while(rs.next()) {
				// יצירת אובייקט חדש של InstructorData לכל רשומה
				ins[i]=new InstructorData();
				// הגדרת המאפיינים של האובייקט עם הנתונים מהשאילתה
				ins[i].name=rs.getString("tname");// שם המורה
				ins[i].code=rs.getString("tcode");// קוד המורה
				ins[i].course[0]=rs.getString("tsub");// הקורס שהמורה מלמד
				// לולאה לבדיקה איזה קורסים המורה מלמד ושיוך המורה לרשימת המורים של הקורס
				for(k=0;k<nocourse;k++) {
					// אם נמצא קורס מתאים, הוספת קוד המורה לרשימת המורים המלמדים בקורס זה
					if(course[k].code.equals(ins[i].course[0]))
						course[k].ins[course[k].noins++]=ins[i].code;
				}
				i++;
			}
			// שמירת מספר המורים שנטענו
			noins=i;

			// ביצוע שאילתה SQL לקבלת כל הרשומות מטבלת classrooms
			rs=st.executeQuery("SELECT * FROM classrooms");
			i=0;// איתחול מונה
			// לולאה שקוראת כל רשומה מתוצאות השאילתה
			while(rs.next()) {
				// יצירת אובייקט חדש של ClassData לכל רשומה
				rooms[i]=new ClassData();
				// הגדרת המאפיינים של האובייקט עם הנתונים מהשאילתה
				rooms[i].code=rs.getString("code");// קוד הכיתה
				rooms[i].department=rs.getString("dep");// מחלקה אליה שייכת הכיתה
				rooms[i].strength=Integer.parseInt(rs.getString("strength"));// הכושר, כלומר מספר המקומות בכיתה
				i++; // הגדלת המונה
			}
			noroom=i;// שמירת מספר הכיתות שנטענו

			cn.close();// סגירת החיבור למסד הנתונים
		}

		catch (Exception e) {
		}	
	}

	int returnClassNo() {return(noroom);} // מחזירה את מספר הכיתות
	int returnInstructorNo() {return(noins);}// מחזירה את מספר המדריכים
	int returnStudentGrpNo() {return(nostgrp);}// מחזירה את מספר קבוצות התלמידים
	int returnCourseNo() {return(nocourse);} // מחזירה את מספר הקורסים
}

// מחלקה המייצגת גן במודל הגנטי:
class Gene {
	int[] g;// מערך של ערכים בינאריים המייצגים את הגן
	Gene(int course, int stdgrp, int ins) {
		// מחשב את אורך המערך בהתאם למספר הביטים הדרושים לייצוג בינארי של כל מרכיב
		g=new int[Integer.toBinaryString(course).length()+Integer.toBinaryString(stdgrp).length()+Integer.toBinaryString(ins).length()];
	}
}

// מחלקה המייצגת כרומוזום במודל הגנטי:
class Chromosome {
	Gene[] period;	// מערך של גנים המייצגים את הכרומוזום
	// בנאי שמייצר כרומוזום חדש עבור כל החדרים במערכת:
	Chromosome(int rooms) {
		// מייצר מערך של גנים, כאשר כל חדר מיוצג ב-35 תקופות (5 ימים עבודה, 7 שעות עבודה ביום)
		period=new Gene[rooms*5*7]; //no. of working days=5 & no. of working hours each day=7(9:30 to 13:30 and 14:30 to 17:30)
	}

	// פונקציה שמחשבת את הכושר הגנטי של כרומוזום, על פי מידת התאמת הפתרון שהוא מייצג
	double fitness(InputData input, int norooms, int nocourse, int nostgrp, int noins) {
		// הגדרת משתנים לחישוב
		int k,i,j,room=-1,flag;
		double fitnessvalue=0,fit=0;
		// מחשב את אורך הבינארי של מספר הקורסים, קבוצות ומורים לצורך עיבוד הכרומוזום
		int lcourse=Integer.toBinaryString(nocourse).length();
		int lstgrp=Integer.toBinaryString(nostgrp).length();
		int lins=Integer.toBinaryString(noins).length();
		// הגדרת מחרוזות להחזקת המזהים הבינאריים של קורסים, קבוצות ומורים
		String course="",stgrp="",ins="",tempst="",tempin="";
		int st;

		// עיבוד כל התקופות בכרומוזום
		for(i=0;i<norooms*5*7;i++)
		{
			if(i%35==0)// בכל תחילת שבוע (35 שעות בשבוע) מעדכן את החדר הנוכחי
				room++;

			// קריאה והמרה של הקוד הבינארי של הקורס למחרוזת
			course="";
			for(j=0;j<lcourse;j++)
				course=course+period[i].g[j];

			// קריאה והמרה של הקוד הבינארי של קבוצת הסטודנטים למחרוזת
			stgrp="";
			for(j=lcourse;j<lcourse+lstgrp;j++)
				stgrp=stgrp+period[i].g[j];

			// קריאה והמרה של הקוד הבינארי של המורה למחרוזת
			ins="";
			for(j=lcourse+lstgrp;j<lcourse+lstgrp+lins;j++)
				ins=ins+period[i].g[j];

			// המרת מזהה הקבוצה מבינארי לשלם
			st=Integer.valueOf(stgrp,2);

			// תנאי שבודק אם החדר מספיק גדול לקבוצת הסטודנטים, ואם כן מגדיל את ספירת התאמה
			if(input.rooms[room].strength>=input.stgrp[st].strength)
				fit++;

			// בדיקה למניעת חזרתיות של קבוצת סטודנטים בחדרים אחרים
			flag=1;
			for(j=i+35;j<norooms*5*7;j+=35) {
				tempst="";
				for(k=lcourse;k<lcourse+lstgrp;k++)
					tempst=tempst+period[j].g[k];
				if(tempst.equals(stgrp))
					flag=0;
			}
			for(j=i-35;j>=0;j-=35) {
				tempst="";
				for(k=lcourse;k<lcourse+lstgrp;k++)
					tempst=tempst+period[j].g[k];
				if(tempst.equals(stgrp))
					flag=0;
			}
			// תוספת לספירת התאמה אם הקבוצה לא חוזרת
			if(flag==1)
				fit++;

			// בדיקה למניעת חזרתיות של מורים בחדרים אחרים
			flag=1;
			for(j=i+35;j<norooms*5*7;j+=35) {
				tempin="";
				for(k=lcourse+lstgrp;k<lcourse+lstgrp+lins;k++)
					tempin=tempin+period[j].g[k];
				if(tempin.equals(ins)) {	
					flag=0;
					break;
				}
			}
			for(j=i-35;j>=0;j-=35) {
				tempin="";
				for(k=lcourse+lstgrp;k<lcourse+lstgrp+lins;k++)
					tempin=tempin+period[j].g[k];
				if(tempin.equals(ins)) {
					flag=0;
					break;
				}
			}
			// תוספת לספירת התאמה אם המורה לא חוזר
			if(flag==1)
				fit++;
		}

		// חישוב ערך הכושר הכולל והחזרתו
		fitnessvalue=fit/(norooms*35);
		return(fitnessvalue);
	}
}

class Table {
	String course; // מחרוזת לשם הקורס או המקצוע שנלמד
	String ins;    // מחרוזת לשם המורה או המרצה שמוביל את השיעור
	String room;   // מחרוזת לפרטי החדר בו מתקיים השיעור

	// בנאי שמאתחל את כל הערכים למחרוזות ריקות
	Table(){
		course="";
		ins="";
		room="";
	}
}

class TimeTable {
	private Chromosome sol;  // כרומוזום שמחזיק את הגנים ללוח הזמנים
	static Table[][][] ttable;// מערך תלת-ממדי של טבלאות, כל אחת מייצגת תא בלוח השנה
	InputData input;// מחלקה שמחזיקה את כל הקלטים כמו פרטי הקורסים והמורים
	int nostgrp,noclass,noins,nocourse;// מספר הקבוצות, כיתות, מורים, וקורסים
	// בנאי המחלקה
	TimeTable(Chromosome solution, int stgrp1, int nclass, int ins, int course, InputData input1) {
		sol=solution; // מקבל כרומוזום שמייצג את הפתרון
		nostgrp=stgrp1; // מספר הקבוצות הסטודנטיאליות
		ttable=new Table[nostgrp][7][5];// יצירת מערך תלת-ממדי לייצוג לוח שנה שלם
		input=input1;// הקלט שמכיל נתונים על כל הקורסים, כיתות, וכו'
		int i,j,k;
		// איתחול הטבלאות בלוח הזמנים
		for(i=0;i<nostgrp;i++) {
			for(j=0;j<7;j++) {//פרקי זמן ביום 7
				for(k=0;k<5;k++) //  5 ימים בשבוע
					ttable[i][j][k]=new Table();
			}
		}
		noclass=nclass; // מספר הכיתות
		noins=ins;// מספר המורים
		nocourse=course;  // מספר הקורסים
	}

	// מילוי טבלת הזמנים על פי הכרומוזום שהתקבל
	void findTable() {
		int lcourse=Integer.toBinaryString(nocourse).length();// אורך בינארי של מספר הקורסים
		int lstgrp=Integer.toBinaryString(nostgrp).length();// אורך בינארי של מספר הקבוצות
		int lins=Integer.toBinaryString(noins).length();// אורך בינארי של מספר המורים
		int i,j,k,cs,st,in,room=-1,d=-1,t=0;
		String course,stgrp,ins;

		// עיבוד כל תא בלוח הזמנים
		for(i=0;i<noclass*35;i++) {
			if(i%35==0) {// תחילת יום חדש
				room++;
				d=-1;
			}
			if((i%7==0)) {// תחילת פרק זמן חדש ביום
				t=0;
				d++;
			}

			// קריאת הנתונים מהכרומוזום
			course="";
			for(j=0;j<lcourse;j++)
				course=course+sol.period[i].g[j];

			stgrp="";
			for(j=lcourse;j<lcourse+lstgrp;j++)
				stgrp=stgrp+sol.period[i].g[j];

			ins="";
			for(j=lcourse+lstgrp;j<lcourse+lstgrp+lins;j++)
				ins=ins+sol.period[i].g[j];
			
			st=Integer.valueOf(stgrp,2);// המרה למספר שלם

			cs=Integer.valueOf(course,2);

			in=Integer.valueOf(ins,2);

			// מילוי הטבלה בהתאם לפרטים שנקראו
			ttable[st][t][d]=new Table();
			ttable[st][t][d].course = findCourseNameByCode(input.course[cs].code); // החלפת הקוד עם שם הקורס
			ttable[st][t][d].ins = findInstructorNameByCode(input.ins[in].code); // החלפת הקוד עם שם המורה
			ttable[st][t][d].room = input.rooms[room].department  + input.rooms[room].code; // פרטי החדר
			t++;
		}
		// בדיקה אם נתונים חסרים ומילוי ביניים במקרה של נתונים ריקים
		for(i=0;i<nostgrp;i++) {
			for(j=0;j<7;j++) {
				for(k=0;k<5;k++)
					if(ttable[i][j][k].course.equals("")) {
						ttable[i][j][k].course="--";
						ttable[i][j][k].room="--";
						ttable[i][j][k].ins="--";
					}
			}
		}
		// הדפסה של הטבלה המוכנה
		PrintTable print=new PrintTable(ttable,nostgrp,input);
		print.print();
	}

String findCourseNameByCode(String code) {
		for (CourseData course : input.course) {
			if (course.code.equals(code)) {
				return course.name;
			}
		}
		return "Unknown Course";
	}

String findInstructorNameByCode(String code) {
		for (InstructorData instructor : input.ins) {
			if (instructor.code.equals(code)) {
				return instructor.name;
			}
		}
		return "Unknown Instructor";
	}

}

public class Schedule {
	static Chromosome[] classes;// מערך של כרומוזומים שמייצג את כל האופציות ללוחות זמנים
	static int noclass,nocourse,noins,nostgrp; // מספרי הכיתות, הקורסים, המורים והקבוצות
	static double[] fit;// מערך של ערכי התאמה (fitness) לכל כרומוזום
	static InputData input;// מחלקה שמחזיקה את כל הנתונים הרלוונטיים

	// פונקציה ליצירת אוכלוסיית התחלתית של כרומוזומים
	static void createpopulation()  {
		int i,j,k,l,le,c,s,in,m=0,n;
		boolean flag;
		noclass=input.returnClassNo(); // קבלת מספר הכיתות
		nocourse=input.returnCourseNo();// קבלת מספר הקורסים
		nostgrp=input.returnStudentGrpNo();// קבלת מספר הקבוצות
		noins=input.returnInstructorNo();// קבלת מספר המורים
		Random r=new Random(); // יצירת אובייקט רנדומלי לבחירות אקראיות
		classes=new Chromosome[10000];// הקצאה של מערך כרומוזומים ל-10,000 אפשרויות שונות

		// לולאה ליצירת כל כרומוזום באוכלוסייה
		for(k=0;k<10000;k++) {
			classes[k]=new Chromosome(noclass); // יצירת כרומוזום חדש עבור כל תא במערך
			// לולאה ליצירת הגנים של כל כרומוזום
			for(i=0;i<noclass*5*7;i++) {// כל כיתה מתוכנתת ל-5 ימים בשבוע ו-7 שעות ביום
				m=0;

				l=Integer.toBinaryString(nocourse).length();// אורך ההצגה הבינארית של מספר הקורסים

				// בחירה רנדומלית של קורס
				c=r.nextInt(nocourse);
				classes[k].period[i]=new Gene(nocourse, nostgrp, noins);
				String x=Integer.toBinaryString(c);
				le=x.length();
				// מילוי הגן עם נתוני הקורס
				if(l>le) {
					for(j=0;j<l-le;j++)
						classes[k].period[i].g[m++]=0;// הוספת אפסים בתחילת המחרוזת להשלמה לאורכה הנדרש
				}
				for(j=0;j<le;j++) {
					classes[k].period[i].g[m++]=(x.charAt(j)-'0');// המרת התווים הבינאריים למספרים והכנסתם למערך הגן
				}
				n=m;

				// בחירה רנדומלית של קבוצת סטודנטים, תוך בדיקה שהקורס מתאים לקבוצה
				flag=false;
				while(!flag) {
					s=r.nextInt(nostgrp);
					for(j=0;j<input.course[c].nostgrp;j++) {
						if(input.course[c].stgrp[j].equals(input.stgrp[s].code)) {
							m=n;
							l=Integer.toBinaryString(nostgrp).length();
							x=Integer.toBinaryString(s);
							le=x.length();
							if(l>le) {
								for(j=0;j<l-le;j++)
									classes[k].period[i].g[m++]=0;
							}
							for(j=0;j<le;j++) {
								classes[k].period[i].g[m++]=x.charAt(j)-'0';// המרת התווים הבינאריים למספרים והכנסתם למערך הגן
							}
							flag=true;
							break;
						}
					}
				}	
				n=m;

				// בחירה רנדומלית של מורה, תוך בדיקה שהמורה מלמד את הקורס הנבחר
				flag=false;
				while(!flag) {
					in=r.nextInt(noins);
					for(j=0;j<input.course[c].noins;j++) {
						if(input.course[c].ins[j].equals(input.ins[in].code)) {
							m=n;
							l=Integer.toBinaryString(noins).length();
							x=Integer.toBinaryString(in);
							le=x.length();
							if(l>le) {
								for(j=0;j<l-le;j++)
									classes[k].period[i].g[m++]=0;
							}
							for(j=0;j<le;j++) {
								classes[k].period[i].g[m++]=(x.charAt(j)-'0');
							}
							flag=true;
							break;
						}
					}
				}
				// סיום הכנסת נתונים לגנים
			}
		}
		// סיום יצירת האוכלוסייה ההתחלתית
	}

	// פונקציה לחישוב ערכי התאמה (fitness) לכל הכרומוזומים באוכלוסייה
	static void fitness(int size) {
		fit=new double[size];// יצירת מערך לשמירת ערכי התאמה
		int i;
		// לולאה על כל הכרומוזומים באוכלוסייה
		for(i=0;i<size;i++) {
			// חישוב ערך התאמה עבור כל כרומוזום באמצעות פונקציה שמוגדרת במחלקת Chromosome
			fit[i]=classes[i].fitness(input, noclass, nocourse, nostgrp, noins);
		}
	}

	// פונקציה לצמצום גודל האוכלוסייה על ידי הפעלת אלגוריתם Branch and Bound
	static int branchAndBound(int size) {
		int i,flag,l,j,k=0;
		double small=fit[0],high=fit[0];// התחלה עם הערך הראשון במערך ההתאמה כערך הקטן והגבוה
		double[][] value=new double[1500][2];// מערך לשמירת ערכי התאמה מינימליים ומקסימליים לכל תת-קבוצה

		// חלוקת האוכלוסייה לתת-קבוצות וחישוב ערכים מקסימליים ומינימליים עבור כל תת-קבוצה
		for(i=1000;i>=8;i/=5) {
			k=0;
			for(j=0;j<size;j++) {
				if((j%i==0)&&(j!=0)) {
					value[k][0]=small;
					value[k][1]=high;
					small=fit[j];
					high=fit[j];
					k++;
					continue;
				}
				if(small>fit[j])
					small=fit[j];
				if(high<fit[j])
					high=fit[j];
			}
			// איתור תת-קבוצות שאינן חופפות והסרתן מהאוכלוסייה
			for(j=0;j<k;j++) {
				flag=0;
				for(l=0;l<k;l++) {
					if(l==j)
						continue;
					if(value[j][1]<value[l][0]) {
						flag=1;
						break;
					}
				}
				if(flag==1) {
					for(l=(j*i);l<size-i;l++)
						classes[l]=classes[l+i];
					size=size-i;
				}
			}
		}
		return(size);// מחזיר את הגודל החדש של האוכלוסייה לאחר הצמצום
	}

	// פונקציה לבחירת שני הורים לתהליך החצייה מתוך האוכלוסייה
	static int[] select(int size) {
		int[] select=new int[2];// מערך לשמירת אינדקסים של שני ההורים שנבחרו
		int[] rwheel=new int[100000];// גלגל ההגרלה לבחירת ההורים
		int i,j,k=0,percent;
		double fitsum=0;// סכום כל ערכי ההתאמה

		// חישוב סכום כל ערכי ההתאמה
		for(i=0;i<size;i++) {
			fitsum=fitsum+fit[i];
		}

		// בניית גלגל ההגרלה לפי ערכי ההתאמה
		for(i=0;i<size;i++) {
			percent=(int)((((double)fit[i])/fitsum)*100000);
			for(j=k;j<k+percent;j++)
				rwheel[j]=i;
			k=k+percent;
		}
		Random r=new Random();
		// בחירת ההורה הראשון
		select[0]=rwheel[r.nextInt(100000)];
		// בחירת ההורה השני
		select[1]=rwheel[r.nextInt(100000)];

		return(select);
	}

	//single point crossover
	// פונקציה לביצוע חצייה נקודתית בין שני ההורים
	static void crossover(int[] parent,Chromosome son1,Chromosome son2) {
		Random n1=new Random();
		int r1,row1,col1,i,j;
		int len_course=Integer.toBinaryString(nocourse).length();
		int len_stgrp=Integer.toBinaryString(nostgrp).length();
		int len_ins=Integer.toBinaryString(noins).length();
		int len_gene=len_course+len_stgrp+len_ins;// אורך הגן
		r1=n1.nextInt(len_gene*noclass*35);// בחירת נקודת החצייה באקראי
		row1=r1/(len_gene);// מציאת שורת נקודת החצייה
		col1=r1%len_gene;// מציאת עמודת נקודת החצייה

		//single point crossover
		// חצייה עד נקודת החצייה
		for(i=0;i<row1;i++) {
			son1.period[i]=new Gene(nocourse, nostgrp, noins);
			son2.period[i]=new Gene(nocourse, nostgrp, noins);
			for(j=0;j<len_gene;j++) {
				son1.period[i].g[j]=classes[parent[1]].period[i].g[j];
				son2.period[i].g[j]=classes[parent[0]].period[i].g[j];
			}
		}
		// חצייה בנקודת החצייה
		for(j=0;j<col1;j++) {
			son1.period[row1]=new Gene(nocourse, nostgrp, noins);
			son2.period[row1]=new Gene(nocourse, nostgrp, noins);
			son1.period[row1].g[j]=classes[parent[1]].period[row1].g[j];
			son2.period[row1].g[j]=classes[parent[0]].period[row1].g[j];
		}
		for(j=col1;j<len_gene;j++) {
			son1.period[row1]=new Gene(nocourse,nostgrp,noins);
			son2.period[row1]=new Gene(nocourse,nostgrp,noins);
			son1.period[row1].g[j]=classes[parent[0]].period[row1].g[j];
			son2.period[row1].g[j]=classes[parent[1]].period[row1].g[j];
		}

		// חצייה אחרי נקודת החצייה
		for(i=row1+1;i<noclass*35;i++) {
			son1.period[i]=new Gene(nocourse, nostgrp, noins);
			son2.period[i]=new Gene(nocourse, nostgrp, noins);

			for(j=0;j<len_gene;j++) {
				son1.period[i].g[j]=classes[parent[0]].period[i].g[j];
				son2.period[i].g[j]=classes[parent[1]].period[i].g[j];
			}
		}
	}


	//תהליך זה עובר על כל גן בכרומוזום של הבן ובוחר נקודה אקראית לשינוי. השינוי
	//מתבצע על ידי היפוך הביט באותה נקודה, מה שיכול להשפיע על התכונות שהגן מייצג. זו טכניקה נפוצה
	//באלגוריתמים גנטיים לשינוי קל ואקראי בפנוטיפ של הפתרון, על מנת לחקור אזורים חדשים במרחב
	//הפתרונות ולאפשר שיפור בהתאמת הכרומוזומים לסביבה.
	static void mutation(Chromosome son) {
		int i;
		// מחשב את אורך הביטים הנדרש לייצוג כמות הקורסים, קבוצות הסטודנטים, והמדריכים
		int len_course=Integer.toBinaryString(nocourse).length();
		int len_stgrp=Integer.toBinaryString(nostgrp).length();
		int len_ins=Integer.toBinaryString(noins).length();
		int len_gene=len_course+len_stgrp+len_ins;// סך הכל אורך הגן

		// עובר על כל הגנים בכרומוזום
		for(i=0;i<noclass*35;i++) {
			Random r=new Random();
			// בחירה אקראית של מיקום בכרומוזום
			int pos=r.nextInt(len_gene*35*noclass);
			int row=pos/len_gene;// מחשב את השורה שבה נמצא הגן לשינוי
			int col=pos%len_gene;// מחשב את העמודה בתוך הגן לשינוי

			// ביצוע המוטציה: היפוך ביט (מ-0 ל-1 או מ-1 ל-0)
			if(son.period[row].g[col]==0)
				son.period[row].g[col]=1;
			if(son.period[row].g[col]==1)
				son.period[row].g[col]=0;
		}
	} 


	//הפונקציה Schedule() מהווה את ליבת האלגוריתם הגנטי והיא מנהלת את תהליך היצירה,
	//ההערכה, הבחירה, החציצה (crossover), והמוטציה של פופולציית הכרומוזומים כדי למצוא פתרון
	//אופטימלי לבעיה
	Schedule() {// פונקציה ראשית שמבצעת את תהליך האלגוריתם הגנטי כולל בחירת הורים, חצייה, מוטציה והערכת כלל הפופולציה
		int i=0,j,k=0,l,count=0,g=0;
		Chromosome temp;
		double tempfitt []=new double [5];// מערך לאחסון ערכי התאמה זמניים להשוואת התקדמות האלגוריתם
		double tempfit,maxfit=0;// משתנים לערכי התאמה זמניים ומקסימליים
		Chromosome[] newgen=new Chromosome[10000];// פופולציית כרומוזומים חדשה
		Chromosome[] sonparent=new Chromosome[6]; // מערך לאחסון הורים וצאצאים לצורך חצייה
		double[] fsonparent=new double[6]; // מערך לאחסון ערכי ההתאמה של ההורים והצאצאים
		double fit1,fit2,fitp1,fitp2;// משתנים לאחסון ערכי התאמה במהלך החצייה
		int size=10000;	//size of population // גודל הפופולציה
		int[] parent=new int[2];// מערך לאחסון אינדקסים של ההורים שנבחרו לחצייה
		input=new InputData();// יצירת אובייקט קלט חדש
		input.takeInput();// קליטת הנתונים ממסד הנתונים
		Chromosome maxchrome=new Chromosome(noclass);// יצירת כרומוזומים לשימור הטוב ביותר וזמני
		Chromosome tempchrome=new Chromosome(noclass);

		//chromosome population initialization
		createpopulation();// יצירת הפופולציה ההתחלתית

		//fitness initialization
		fitness(size);// חישוב ערכי ההתאמה הראשוניים לכל הפופולציה
		for(l=0;l<3;l++) {
			// לולאה לביצוע תהליכים עד קבלת תוצאה אופטימלית או למספר מוגבל של איטרציות
			//branch and bound to reduce the size and improve the quality of population set
			size=branchAndBound(size);// יישום שיטת Branch and Bound לשיפור והקטנת גודל הפופולציה

			//fitness calculation
			fitness(size);// חישוב מחדש של ערכי ההתאמה לפופולציה המוגבלת

			for(i=0;i<size/2;i++) {
				//parent selection for crossover operation
				newgen[2*i]=new Chromosome(noclass);
				newgen[2*i+1]=new Chromosome(noclass);

				parent=select(size); // בחירת הורים לחצייה
				fitp1=fit[parent[0]];
				fitp2=fit[parent[1]];

				sonparent[0]=classes[parent[0]];
				sonparent[1]=classes[parent[1]];
				fsonparent[0]=fitp1;
				fsonparent[1]=fitp2;//מאכסן את ערכי ההתאמה במערך אכסון ערכי התאמה

				//crossover
				Chromosome son1=new Chromosome(noclass);
				Chromosome son2=new Chromosome(noclass);
				crossover(parent,son1,son2);// ביצוע חצייה ויצירת שני צאצאים
				fit1=son1.fitness(input, noclass, nocourse, nostgrp, noins);
				fit2=son2.fitness(input, noclass, nocourse, nostgrp, noins);

				sonparent[2]=son1;
				sonparent[3]=son2;
				fsonparent[2]=fit1;
				fsonparent[3]=fit2;

				fit1=son1.fitness(input, noclass, nocourse, nostgrp, noins);
				fit2=son2.fitness(input, noclass, nocourse, nostgrp, noins);

				sonparent[4]=son1;
				sonparent[5]=son2;
				fsonparent[4]=fit1;
				fsonparent[5]=fit2;


				//כעת אחרי ששמרנו את ערכי ההתאמה של ההורים ושל הבנים נחשב מי מהם יותר חזק ונשמור רק את אלו שהכי טובים כלומר עם ערך ההתאמה הכי כבוה
				//sort sonparent on basis of fsonparent
				// מיון הצאצאים לפי ערך ההתאמה כדי לבחור את האופטימליים לדור הבא
				for(j=1;j<6;j++) {
					temp=sonparent[j];
					tempfit=fsonparent[j];
					for(k=j-1;k>=0;k--) {
						if(fsonparent[k]>tempfit) {
							sonparent[k+1]=sonparent[k];
							fsonparent[k+1]=fsonparent[k];
						}
						else 
							break;
					}
					sonparent[k+1]=temp;
					fsonparent[k+1]=tempfit;
				}
				newgen[2*i]=sonparent[5];
				newgen[2*i+1]=sonparent[4];

			}

			// עדכון הפופולציה לדור החדש וחישוב חוזר של ערכי ההתאמה
			for(i=0;i<size;i++) {

				classes[i]=newgen[i];
				fit[i]=newgen[i].fitness(input, noclass, nocourse, nostgrp, noins);
			}

			// חישוב מחדש של הערך הגבוה ביותר ובדיקה אם נמצא פתרון אופטימלי
			tempfit=fit[0];
			for(i=1;i<size;i++)
				if(tempfit<fit[i]) {
					tempfit=fit[i];
					tempchrome=classes[i];
				}
			// תנאי הפסקה - אם התקדמות האלגוריתם עצרה או שהגענו לפתרון מספק
			if(count%5==0)
				tempfitt[count%5]=tempfit;
			if(count%5==1)
				tempfitt[count%5]=tempfit;
			if(count%5==2)
				tempfitt[count%5]=tempfit;
			if(count%5==3)
				tempfitt[count%5]=tempfit;
			if(count%5==4)
				tempfitt[count%5]=tempfit;
			count++;
			if(tempfitt[0]==tempfitt[1] && tempfitt[0]==tempfitt[2] && tempfitt[0]==tempfitt[3] && tempfitt[0]==tempfitt[4] && count>4) {	
				for(g=0;g<size;g++)
					mutation(classes[g]);
			}
			if(maxfit<tempfit) {
				maxfit=tempfit;
				maxchrome=tempchrome;
			}
			if(((maxfit<=3.0)&&(maxfit>=2.7))||(size<=1000))
				break;
		}

		// יצירת מערכת לוח זמנים מהכרומוזום עם הערך הגבוה ביותר והצגת הלוח
		TimeTable table1 = new TimeTable(maxchrome, nostgrp, noclass, noins, nocourse, input);
		table1.findTable();
	}
}