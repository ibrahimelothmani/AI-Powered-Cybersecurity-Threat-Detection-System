import React, { useState } from 'react';
import { Paper, TextField, Button, Typography, Box, Alert, CircularProgress, Container } from '@mui/material';
import URLAnalysisResults from './URLAnalysisResults';
import SearchIcon from '@mui/icons-material/Search';

interface AnalysisResult {
  dns: {
    records: Array<{
      type: string;
      value: string;
    }>;
  };
  ssl: {
    valid: boolean;
    issuer: string;
    expiryDate: string;
  };
  headers: {
    security: {
      'Strict-Transport-Security': string;
      'Content-Security-Policy': string;
      'X-Frame-Options': string;
      'X-Content-Type-Options': string;
      'X-XSS-Protection': string;
    };
  };
  domain: {
    registrar: string;
    creationDate: string;
    expiryDate: string;
  };
  threatLevel: number;
  details: string[];
  timestamp: string;
}

const URLAnalyzer: React.FC = () => {
  const [url, setUrl] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [result, setResult] = useState<AnalysisResult | null>(null);

  const handleAnalyze = async () => {
    if (!url) {
      setError('Please enter a URL');
      return;
    }

    try {
      setLoading(true);
      setError(null);

      const response = await fetch('http://localhost:8000/api/analyze', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ url })
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      setResult({
        dns: data.dns,
        ssl: data.ssl,
        headers: data.headers,
        domain: data.domain,
        threatLevel: data.threat_prob * 100,
        details: data.details || [],
        timestamp: new Date().toLocaleString()
      });
      setLoading(false);
    } catch (err) {
      const errorMessage = err instanceof Error ? err.message : 'An error occurred during analysis';
      setError(errorMessage);
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="lg">
      <Box sx={{ 
        display: 'flex', 
        flexDirection: 'column', 
        alignItems: 'center',
        mt: 4,
        mb: 4
      }}>
        <Typography variant="h4" gutterBottom sx={{ fontWeight: 'bold', mb: 3 }}>
          Website Security Analysis
        </Typography>
        <Paper 
          sx={{ 
            p: 4, 
            width: '100%',
            maxWidth: 800,
            backgroundColor: 'background.paper',
            borderRadius: 2
          }}
        >
          <Box sx={{ display: 'flex', gap: 2 }}>
            <TextField
              fullWidth
              placeholder="Enter website URL (e.g., example.com)"
              variant="outlined"
              value={url}
              onChange={(e) => setUrl(e.target.value)}
              error={Boolean(error)}
              helperText={error}
              disabled={loading}
              sx={{ 
                '& .MuiOutlinedInput-root': {
                  borderRadius: 2,
                  backgroundColor: 'background.default'
                }
              }}
            />
            <Button
              variant="contained"
              onClick={handleAnalyze}
              disabled={loading}
              sx={{ 
                minWidth: 120,
                borderRadius: 2,
                height: 56
              }}
              startIcon={loading ? <CircularProgress size={24} color="inherit" /> : <SearchIcon />}
            >
              {loading ? 'Analyzing' : 'Analyze'}
            </Button>
          </Box>
        </Paper>

        {error && (
          <Alert severity="error" sx={{ mt: 2, width: '100%', maxWidth: 800 }}>
            {error}
          </Alert>
        )}

        {result && <URLAnalysisResults results={result} />}
      </Box>
    </Container>
  );
};

export default URLAnalyzer;