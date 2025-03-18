import React from 'react';
import {
    Accordion,
    AccordionSummary,
    AccordionDetails,
    Typography,
    Box,
    Card,
    CardContent,
    Chip,
    List,
    ListItem,
    ListItemText,
    Grid,
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import DnsIcon from '@mui/icons-material/Dns';
import SecurityIcon from '@mui/icons-material/Security';
import HttpIcon from '@mui/icons-material/Http';
import LanguageIcon from '@mui/icons-material/Language';

interface DNSRecord {
  type: string;
  value: string;
}

interface SSLInfo {
  valid: boolean;
  issuer: string;
  expiryDate: string;
}

interface SecurityHeaders {
  security: {
    'Strict-Transport-Security': string;
    'Content-Security-Policy': string;
    'X-Frame-Options': string;
    'X-Content-Type-Options': string;
    'X-XSS-Protection': string;
  };
}

interface DomainInfo {
  registrar: string;
  creationDate: string;
  expiryDate: string;
}

interface AnalysisResults {
  dns: {
    records: DNSRecord[];
  };
  ssl: SSLInfo;
  headers: SecurityHeaders;
  domain: DomainInfo;
}

interface URLAnalysisResultsProps {
  results: AnalysisResults;
}

const URLAnalysisResults: React.FC<URLAnalysisResultsProps> = ({ results }) => {
  return (
    <Box sx={{ mt: 3 }}>
      <Accordion defaultExpanded>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <DnsIcon sx={{ mr: 1 }} />
            <Typography variant="h6">DNS Records</Typography>
          </Box>
        </AccordionSummary>
        <AccordionDetails>
          <Grid container spacing={2}>
            {results.dns.records.map((record, index) => (
              <Grid item xs={12} sm={6} md={4} key={index}>
                <Card variant="outlined">
                  <CardContent>
                    <Typography variant="h6" color="primary" gutterBottom>
                      {record.type}
                    </Typography>
                    <Typography variant="body2" sx={{ wordBreak: 'break-all' }}>
                      {record.value}
                    </Typography>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        </AccordionDetails>
      </Accordion>

      <Accordion>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <SecurityIcon sx={{ mr: 1 }} />
            <Typography variant="h6">SSL Certificate</Typography>
          </Box>
        </AccordionSummary>
        <AccordionDetails>
          <Card variant="outlined">
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <Chip
                  label={results.ssl.valid ? 'Valid' : 'Invalid'}
                  color={results.ssl.valid ? 'success' : 'error'}
                  sx={{ mr: 2 }}
                />
              </Box>
              <Typography variant="body1" gutterBottom>
                <strong>Issuer:</strong> {results.ssl.issuer}
              </Typography>
              <Typography variant="body1">
                <strong>Expiry Date:</strong> {results.ssl.expiryDate}
              </Typography>
            </CardContent>
          </Card>
        </AccordionDetails>
      </Accordion>

      <Accordion>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <HttpIcon sx={{ mr: 1 }} />
            <Typography variant="h6">Security Headers</Typography>
          </Box>
        </AccordionSummary>
        <AccordionDetails>
          <List>
            {Object.entries(results.headers.security).map(([header, value]) => (
              <ListItem key={header}>
                <ListItemText
                  primary={header}
                  secondary={value}
                  secondaryTypographyProps={{
                    sx: { wordBreak: 'break-all' },
                  }}
                />
              </ListItem>
            ))}
          </List>
        </AccordionDetails>
      </Accordion>

      <Accordion>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <LanguageIcon sx={{ mr: 1 }} />
            <Typography variant="h6">Domain Information</Typography>
          </Box>
        </AccordionSummary>
        <AccordionDetails>
          <Card variant="outlined">
            <CardContent>
              <Typography variant="body1" gutterBottom>
                <strong>Registrar:</strong> {results.domain.registrar}
              </Typography>
              <Typography variant="body1" gutterBottom>
                <strong>Creation Date:</strong> {results.domain.creationDate}
              </Typography>
              <Typography variant="body1">
                <strong>Expiry Date:</strong> {results.domain.expiryDate}
              </Typography>
            </CardContent>
          </Card>
        </AccordionDetails>
      </Accordion>
    </Box>
  );
};

export default URLAnalysisResults;