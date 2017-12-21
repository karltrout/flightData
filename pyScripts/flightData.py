# Function definition is here
def collectAndMinimalizeClimbData( records ):
    climb = []
    isClimbing = False
    levelCnt = 0
    cruiseCnt = 0
    maxCruiseCnt = 20
    accentStartInd = 0
    recIdx = 5
    maxAlt = 0
    alts= ''
    #Starting record is in the 5th spot we will look back 5 records to get an indication of our direction
    while recIdx < len(records) - 15:
        recIdx += 1
        if records[recIdx].pressure_altitude is None or records[recIdx-5].pressure_altitude is None or records[recIdx+5].pressure_altitude is None: continue
        curAlt = float(records[recIdx].pressure_altitude) # if records[recIdx].pressure_altitude is not None else 0
        preAlt = float(float(curAlt) - float(records[recIdx-5].pressure_altitude)) # if records[recIdx-5].pressure_altitude is not None else 0
        nextAlt = float(float(records[recIdx+5].pressure_altitude) - float(curAlt)) # if records[recIdx+5].pressure_altitude is not None else 0
        alts = str(curAlt)+" : "+str(preAlt)+" : "+str(nextAlt)
        #Assending Check
        if nextAlt > 0.0 and nextAlt >= (preAlt * 0.65):
            if levelCnt > 0 :
                climb.append(records[recIdx-levelCnt])
                climb.append(records[recIdx])
                levelCnt = 0
            if accentStartInd == 0:
                accentStartInd = recIdx
            cruiseCnt = 0
            isClimbing = True
        #Descending Check
        elif nextAlt < 0.0 and nextAlt < (-1*curAlt*.0125):
            if levelCnt > 0 :
                climb.append(records[recIdx-levelCnt])
                climb.append(records[recIdx])
                levelCnt = 0
            accentStartInd = 0
            cruiseCnt = 0
        #Leveling Check
        elif nextAlt > 0.0 and nextAlt < (preAlt * .65):
            if levelCnt == 0 and accentStartInd != 0:
                oneThird = int(( recIdx - accentStartInd ) * 1/3)
                twoThirds = int(( recIdx - accentStartInd ) * 2/3)
                climb.append(records[ recIdx - twoThirds ])
                climb.append(records[ recIdx - oneThird ])
                accentStartInd = 0
            levelCnt += 1
            cruiseCnt = 0
        else:
            if not isClimbing: continue
            if levelCnt > 0 :
                climb.append(records[recIdx-levelCnt])
                climb.append(records[recIdx])
                levelCnt = 0
            cruiseCnt += 1
        if isClimbing and cruiseCnt > maxCruiseCnt :
            climb.append(records[recIdx - maxCruiseCnt -1])
            break
    return climb

def maximum_alt(a):
     max = 0;
     for i in a:
             if i.pressure_altitude > max:
                     max = i.pressure_altitude
     return max
