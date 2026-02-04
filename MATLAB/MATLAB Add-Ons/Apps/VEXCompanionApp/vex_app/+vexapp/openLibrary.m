function openLibrary(spkg)
% Copyright 2018 The MathWorks Inc.
% This function is a utility usd by the VEX Companion app to open
% different simulink libraries

if strcmp(spkg,'cortex')
    lib = 'vexarmcortexlib';
elseif strcmp(spkg, 'roboticsPlayground')
    lib = 'RoboticsPlayground';
else
    lib = 'vexv5lib';
end

open_system(lib);

end