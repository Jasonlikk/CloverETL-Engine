integer int1 = 1;
integer[] intList1 = [1];
integer[] intList2 = [1, 2];

integer cnt1;
integer cnt2;
integer cnt3;
integer cnt4;
integer cnt5;
integer cnt6;
integer cnt7;
integer cnt8;
integer cnt9;

string str1 = "str1";
string str2 = "str2";
string str3 = "str3";
string str4 = "str4";
string str5 = "str5";
string[] strList1 = ["strList1"];
string[] strList2 = ["strList2"];
string[] strList3 = ["strList3_1", "strList3_2"];
string[] strList4 = ["strList4"];
string[] strList5 = ["strList5"];

map[string, string] strMap1;
strMap1["strMap1_key1"] = "strMap1_value1";
strMap1["strMap1_key2"] = "strMap1_value2";

map[string, string] strMap2;
strMap2["strMap2_key1"] = "strMap2_value1";
strMap2["strMap2_key2"] = "strMap2_value2";
strMap2["strMap1_key2"] = "strMap1_value2_overwritten";

long long1 = 1L;
long long2 = 2L;

decimal decimal1 = 1D;
decimal decimal2 = 2D;
decimal decimal3 = 3D;
decimal decimal4 = 4D;

number num1 = 1.0;
number num2 = 2.0;
number num3 = 3.0;

firstInput myRecord1;
firstInput tmpRecord;

firstInput[] recordList1;
map[string, firstInput] recordMap1;

map[string, string] singleEvaluationTest;
map[string, firstInput] singleEvaluationMap;

string[] mergeTest;

integer minus = 1;
integer multiply = 2;
integer divide = 10;
integer modulus = 11;

string nullAppend = "nullAppend_";

integer counter = 0;

function integer echo(integer i) {
	counter++;
	return i;
}

function string echo(string s) {
	counter++;
	return s;
}

function string[] echo(string[] l) {
	counter++;
	return l;
}

function map[string, string] echo(map[string, string] m) {
	counter++;
	return m;
}

function map[string, firstInput] echo2(map[string, firstInput] m) {
	counter++;
	return m;
}

function integer transform() {

	int1 += 2;	
	intList1[0] += 2;
	intList2[echo(1)] += 2;
	cnt1 = counter;
	counter = 0;
	
	str1 += "_append";
	str2 += 2; // string concatenation, automatic RHS cast to string
	str3 += true;
	str4 += [1L, 2L];
	str5 += $in.0;
	strList1[0] += "_append";
	strList2 += strList3;
	echo(strList4)[0] += "_strList4append";
	cnt2 = counter;
	counter = 0;
	strList5 += (["l1"] + ["l2"]);
	strMap1["strMap1_key1"] += "_strMap1_append";
	strMap1 += strMap2;
	
	long1 += 2L;
	long2 += 3;
	
	decimal1 += 2D;
	decimal2 += 2;
	decimal3 += 2L;
	decimal4 += 2.5;
	
	num1 += 2.0;
	num2 += 2;
	num3 += 2L;
	
	dictionary.sVerdon += "_sVerdonAppend";
	dictionary.l452 += 2;
	dictionary.d621 += 2.5;
	dictionary.n9342 += 2L;
	dictionary.stringList[0] += "_1";
	dictionary.stringList[echo(1)] += "_2";
	cnt3 = counter;
	counter = 0;
	dictionary.stringList += ["dictionary.stringList_1", "dictionary.stringList_2"];
	map[string, string] dictionaryMap_new;
	dictionaryMap_new["key1"] = "value1";
	dictionaryMap_new["key2"] = "value2";
	dictionary.stringMap += dictionaryMap_new;
	dictionary.stringMap[echo("key2")] += "_dictionaryMap_append";
	cnt4 = counter;
	counter = 0;
	
	$out.0.* = $in.0.*;
	$out.0.Name += "_out0Name_append";
	$out.0.Age += 2L;
	$out.0.BornMillisec += 2;
	$out.0.Value += 2;
	$out.0.Currency += 2.0;
	
	myRecord1.* = $in.0.*;
	myRecord1.Name += "_myRecord1_append";
	myRecord1.Age += 2L;
	myRecord1.BornMillisec += 2;
	myRecord1.Value += 2;
	myRecord1.Currency += 2.0;
	
	recordList1 = [tmpRecord, tmpRecord];
	recordList1[0].* = $in.0.*;
	recordList1[0].Name += "_recordList1_append";
	recordList1[echo(0)].Age += 2L;
	cnt5 = counter;
	counter = 0;
	recordList1[0].BornMillisec += 2;
	recordList1[0].Value += 2;
	recordList1[0].Currency += 2.0;
	
	recordMap1["key"] = tmpRecord;
	recordMap1["key"].* = $in.0.*;
	recordMap1["key"].Name += "_recordMap1_append";
	recordMap1[echo("key")].Age += 2L;
	cnt6 = counter;
	counter = 0;
	recordMap1["key"].BornMillisec += 2;
	recordMap1["key"].Value += 2;
	recordMap1["key"].Currency += 2.0;
	
	singleEvaluationTest["key"] = "val";
	string singleEvaluationTestResult = echo(singleEvaluationTest)[echo("key")] += 555;
	cnt7 = counter;
	counter = 0;
	
	singleEvaluationMap["key"] = tmpRecord;
	singleEvaluationMap["key"].Name = "singleEvaluationMap";
	singleEvaluationTestResult = echo2(singleEvaluationMap)[echo("key")].Name += 123;
	cnt8 = counter;
	counter = 0;
	
	minus -= 3;
	multiply *= 3;
	divide /= 3;
	modulus %= 3;
	
	nullAppend += null;
	
	mergeTest = ["mergeTest_"];
	string[] emptyList1;
	string[] emptyList2;
	mergeTest[length(emptyList1 + emptyList2)] += "mergeTestAppend";
	
	$out.firstMultivalueOutput.stringListField = ["stringListField_"];
	echo($out.firstMultivalueOutput.stringListField)[echo(0)] += "stringListFieldAppend";
	cnt9 = counter;
	counter = 0;
	
	return 0;
}