function path = getExamplesPath(spkg)
% Copyright 2018 The MathWorks Inc.

% This function gets the location of the examples html with the support package
% documentation it uses internal codertaret methods for future
% compatibility

if strcmp(spkg,'cortex')
    path=fullfile(codertarget.internal.vexarmcortex.getDocRoot, 'examples.html');
else
    path=fullfile(codertarget.internal.vexv5.getDocRoot, 'examples.html');
end

end
